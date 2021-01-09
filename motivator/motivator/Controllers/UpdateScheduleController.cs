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
        TargeterContext db = new TargeterContext();
        
        //TODO delete test get method, add post method to run new job or rerun existing
        [HttpGet]
        public IEnumerable<Motivation> Get()
        {
            return db.Motivations.ToList();
        }
        
    }
}