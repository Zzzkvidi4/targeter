using System;
using System.Collections.Generic;

#nullable disable

namespace motivator.Models
{
    public partial class Motivation
    {
        public long Id { get; set; }
        public string Text { get; set; }
        public long? TargetCategoryId { get; set; }

        public virtual TargetCategory TargetCategory { get; set; }
    }
}
