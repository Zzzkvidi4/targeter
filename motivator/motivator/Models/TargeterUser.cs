using System;
using System.Collections.Generic;

#nullable disable

namespace motivator.Models
{
    public partial class TargeterUser
    {
        public TargeterUser()
        {
            TargetCategories = new HashSet<TargetCategory>();
            Targets = new HashSet<Target>();
        }

        public long Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }

        public virtual ICollection<TargetCategory> TargetCategories { get; set; }
        public virtual ICollection<Target> Targets { get; set; }
    }
}
