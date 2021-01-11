using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Writers;
using motivator.Models;
using motivator.Motivator;

namespace motivator
{
    public class Program
    {
        public static IMotivator Motivator = new Motivator.Motivator(/*new TargeterContext()*/);
        public static void Main(string[] args)
        { 
            Motivator.Start();
            //new Motivator.Motivator().Start();
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder => { webBuilder.UseStartup<Startup>(); });
    }
}