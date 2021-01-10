using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using Npgsql;
using System;
using System.Data.Common;
using System.Data;

namespace motivator
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers();
            //services.AddDbContext<TargeterDBContext>();
            services.AddSwaggerGen(c => { c.SwaggerDoc("v1", new OpenApiInfo {Title = "motivator", Version = "v1"}); });
            //services.AddScoped(typeof(DbConnection), (IServiceProvider) => InitializeDatabase());
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "motivator v1"));
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints => { endpoints.MapControllers(); });
        }

        /*DbConnection InitializeDatabase()
        {
            DbConnection connection;
            connection = GetPostgreSqlConnection();
            connection.Open();
            return connection;
        }

        void SetDbConfigOptions(NpgsqlConnectionStringBuilder connectionString)
        {
            // [START cloud_sql_postgres_dotnet_ado_limit]
            // MaxPoolSize sets maximum number of connections allowed in the pool.
            connectionString.MaxPoolSize = 5;
            // MinPoolSize sets the minimum number of connections in the pool.
            connectionString.MinPoolSize = 0;
            // [END cloud_sql_postgres_dotnet_ado_limit]
            // [START cloud_sql_postgres_dotnet_ado_timeout]
            // Timeout sets the time to wait (in seconds) while
            // trying to establish a connection before terminating the attempt.
            connectionString.Timeout = 15;
            // [END cloud_sql_postgres_dotnet_ado_timeout]
            // [START cloud_sql_postgres_dotnet_ado_lifetime]
            // ConnectionIdleLifetime sets the time (in seconds) to wait before
            // closing idle connections in the pool if the count of all
            // connections exceeds MinPoolSize.
            connectionString.ConnectionIdleLifetime = 300;
            // [END cloud_sql_postgres_dotnet_ado_lifetime]
        }

        DbConnection NewPostgreSqlUnixSocketConnection()
        {
            // [START cloud_sql_postgres_dotnet_ado_connection_socket]
            // Equivalent connection string:
            // "Server=<dbSocketDir>/<INSTANCE_CONNECTION_NAME>;Uid=<DB_USER>;Pwd=<DB_PASS>;Database=<DB_NAME>"
            String dbSocketDir = "/cloudsql";
            String instanceConnectionName = Environment.GetEnvironmentVariable("targeter:us-central1:targeter-db");
            var connectionString = new NpgsqlConnectionStringBuilder()
            {
                // The Cloud SQL proxy provides encryption between the proxy and instance.
                SslMode = SslMode.Disable,
                // Remember - storing secrets in plaintext is potentially unsafe. Consider using
                // something like https://cloud.google.com/secret-manager/docs/overview to help keep
                // secrets secret.
                Host = String.Format("{0}/{1}", dbSocketDir, instanceConnectionName),
                Username = Environment.GetEnvironmentVariable("postgres"), // e.g. 'my-db-user
                Password = Environment.GetEnvironmentVariable("postgres"), // e.g. 'my-db-password'
                Database = Environment.GetEnvironmentVariable("targeter"), // e.g. 'my-database'

            };
            connectionString.Pooling = true;
            // Specify additional properties here.
            // [START_EXCLUDE]
            SetDbConfigOptions(connectionString);
            // [END_EXCLUDE]
            NpgsqlConnection connection =
                new NpgsqlConnection(connectionString.ConnectionString);
            // [END cloud_sql_postgres_dotnet_ado_connection_socket]
            return connection;
        }

        DbConnection GetPostgreSqlConnection()
        {
            // [START cloud_sql_postgres_dotnet_ado_backoff]
            var connection = Policy
                .HandleResult<DbConnection>(conn => conn.State != ConnectionState.Open)
                .WaitAndRetry(new[]
                {
                    TimeSpan.FromSeconds(1),
                    TimeSpan.FromSeconds(2),
                    TimeSpan.FromSeconds(5)
                }, (result, timeSpan, retryCount, context) =>
                {
                    // Log any warnings here.
                })
                .Execute(() =>
                {
                    // Return a new connection.
                    // [START_EXCLUDE]
                    if (Environment.GetEnvironmentVariable("DB_HOST") != null)
                    {
                        return NewPostgreSqlTCPConnection();
                    }
                    else
                    {
                        return NewPostgreSqlUnixSocketConnection();
                    //}
                    // [END_EXCLUDE]
                });
            // [END cloud_sql_postgres_dotnet_ado_backoff]
            return connection;
        }*/
    }
}