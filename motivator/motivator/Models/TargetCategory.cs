using System;
using System.Collections.Generic;

#nullable disable

namespace motivator.Models
{
    public partial class TargetCategory
    {
        public TargetCategory()
        {
            Motivations = new HashSet<Motivation>();
            Targets = new HashSet<Target>();
        }

        public long Id { get; set; }
        public long? UserId { get; set; }
        public string Name { get; set; }

        public virtual TargeterUser User { get; set; }
        public virtual ICollection<Motivation> Motivations { get; set; }
        public virtual ICollection<Target> Targets { get; set; }
    }
}
