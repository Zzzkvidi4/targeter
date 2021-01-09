using System.Data.Entity;
using System.Linq;
using motivator.Models;
using Quartz;
using Quartz.Impl;

namespace motivator.Motivator
{
    public class Motivator
    {
        private readonly TargeterContext _db = new TargeterContext();
        
        public async void Start()
        {
            var factory = new StdSchedulerFactory();
            var scheduler = await factory.GetScheduler();
            await scheduler.Start();

            var targets = _db.Targets.Include(t => t.ScheduleNavigation).ToList();
            foreach (var target in targets)
            {
                var schedule = target.ScheduleNavigation; //TODO
                var user = _db.TargeterUsers.First(u => u.Id == target.UserId);

                var vkUserId = VkUserId(user/*target.User*/); //TODO use lazy load

                if (string.IsNullOrEmpty(vkUserId))
                {
                    continue;
                }
                
                var job = JobBuilder.Create<SendMotivationJob>()
                    .WithIdentity("job" + target.Id, "send_motivation")
                    .UsingJobData("vkUserId", vkUserId) 
                    .UsingJobData("targetCategoryId", target.TargetCategoryId)
                    .Build();
                var trigger = TriggerBuilder.Create()
                    .WithIdentity("trigger" + target.Id, "send_motivation")
                    .StartNow()
                    .WithSimpleSchedule(x => x
                        .WithIntervalInSeconds(30)
                        .RepeatForever()
                    )
                    .Build();
                await scheduler.ScheduleJob(job, trigger);
            }
        }

        private static string VkUserId(TargeterUser user)
        {
            return user.Username.Split(":")[^1];
        }
    }
}