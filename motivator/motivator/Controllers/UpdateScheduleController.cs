using System.Collections.Generic;
using System.IO;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using motivator.Dtos;
using motivator.Models;

namespace motivator.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class UpdateScheduleController : ControllerBase
    {
        
        [HttpPost]
        public void Update([FromBody] TargetDto targetDto)
        {
            if (targetDto?.targetId != null)
            {
                Program.Motivator.Rerun(targetDto.targetId.Value);
            }
        }
    }
}