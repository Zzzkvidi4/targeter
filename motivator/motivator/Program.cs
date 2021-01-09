using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace motivator
{
    public class Program
    {
        public static void Main(string[] args)
        {
            new Motivator.Motivator().Start();
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder => { webBuilder.UseStartup<Startup>(); });
    }
}