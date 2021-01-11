using System;
using System.Linq;
using System.Threading.Tasks;
using motivator.Models;
using motivator.Vk;
using Quartz;

namespace motivator.Motivator
{
    public class SendMotivationJob : IJob
    {
        private readonly TargeterContext _db = new TargeterContext();
        private readonly Random _random = new Random();
        private readonly MsgSender _msgSender = new MsgSender();

        public SendMotivationJob(TargeterContext db)
        {
            _db = db;
        }

        public async Task Execute(IJobExecutionContext context)
        {
            var dataMap = context.JobDetail.JobDataMap;
            var vkUserId = dataMap.GetLongValue("vkUserId");
            var targetCategoryId = dataMap.GetLongValue("targetCategoryId");

            var generateMsg = GenerateMsg(targetCategoryId);
            await _msgSender.Send(generateMsg, vkUserId);
        }

        private string GenerateMsg(long targetCategoryId)
        {
            var motivations = _db.Motivations.Where(m => m.TargetCategoryId == targetCategoryId).ToList();
            var index = _random.Next(motivations.Count());
            return motivations.ElementAt(index).Text;
        }
    }
}