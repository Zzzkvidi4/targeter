using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using motivator.Models;
using Quartz;
using Quartz.Impl;

namespace motivator.Motivator
{
    public class Motivator : IMotivator
    {
        private readonly TargeterContext _db = new TargeterContext();
        private StdSchedulerFactory _factory;
        private IScheduler _scheduler;

        /*public Motivator(TargeterContext db)
        {
            //_db = db;
        }*/

        private async void Init()
        {
            _factory = new StdSchedulerFactory();
            _scheduler = await _factory.GetScheduler();
            await _scheduler.Start();
        }
        
        public async void Start()
        {
            Init();
            var targets = _db.Targets
                //.Where(t => t.Status == Status.InProgress) //TODO
                .Where(t => t.Schedule != null)
                .Include(t => t.ScheduleNavigation)
                .ToList();
            foreach (var target in targets)
            {
                var schedule = _db.Schedules.First(s => s.Id == target.Schedule);
                if (schedule == null || schedule.Cron == null || schedule.Cron == "")
                {
                    continue;
                }
                await Run(schedule);
            }
        }

        public async void Rerun(long targetId)
        {
            if (_scheduler == null)
            {
                Init();
            }
            var target = _db.Targets.First(t => t.Id == targetId);
            var schedule = _db.Schedules.First(s => s.Id == target.Id);
            await Run(schedule);
        }

        private async Task Run(Schedule schedule)
        {
            var target = _db.Targets.First(t => t.Schedule == schedule.Id);
            var user = _db.TargeterUsers.First(u => u.Id == target.UserId);
            var vkUserId = VkUserId(user); //TODO use lazy load target.User
            if (string.IsNullOrEmpty(vkUserId))
            {
                return;
            }

            var trigger = TriggerBuilder.Create()
                .WithIdentity("trigger" + target.Id, "send_motivation")
                //.StartAt(schedule.BeginTime)
                .StartNow()
                //.WithSchedule(CronScheduleBuilder.CronSchedule("0/5 * * * * ?"))
                //.WithCronSchedule(/*schedule.Cron*/"0/5 * * * * ?")
                .WithSimpleSchedule(x => x
                    .WithIntervalInSeconds(5)
                    .RepeatForever()
                )
                .Build();
            var job = JobBuilder.Create<SendMotivationJob>()
                .WithIdentity("job" + target.Id, "send_motivation")
                .UsingJobData("vkUserId", vkUserId) 
                .UsingJobData("targetCategoryId", target.TargetCategoryId)
                .Build();
            await _scheduler.ScheduleJob(job, new List<ITrigger> { trigger }, true);
        }

        private static string VkUserId(TargeterUser user)
        {
            return user.Username.Split(":")[^1];
        }
    }
}