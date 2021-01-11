using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using motivator.Models;

namespace motivator.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class UpdateScheduleController : ControllerBase
    {
        
        [HttpPost]
        public void Update(long targetId)
        {
            Program.Motivator.Rerun(targetId);
        }
    }
}