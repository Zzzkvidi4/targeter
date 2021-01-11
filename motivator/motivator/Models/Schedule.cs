using System;
using System.Collections.Generic;

#nullable disable

namespace motivator.Models
{
    public partial class Schedule
    {
        public Schedule()
        {
            Targets = new HashSet<Target>();
        }

        public long Id { get; set; }
        public DateTime BeginTime { get; set; }
        public TimeSpan? Interval { get; set; }
        public TimeSpan[] WeekDaysTime { get; set; }
        public string Cron { get; set; }

        public virtual ICollection<Target> Targets { get; set; }
    }
}
