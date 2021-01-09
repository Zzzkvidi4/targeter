using System;
using System.Collections.Generic;

#nullable disable

namespace motivator.Models
{
    public partial class Target
    {
        public long Id { get; set; }
        public long UserId { get; set; }
        public string Text { get; set; }
        public long TargetCategoryId { get; set; }
        public long? Schedule { get; set; }
        public byte[] PhotoReport { get; set; }

        public virtual Schedule ScheduleNavigation { get; set; }
        public virtual TargetCategory TargetCategory { get; set; }
        public virtual TargeterUser User { get; set; }
    }
}
