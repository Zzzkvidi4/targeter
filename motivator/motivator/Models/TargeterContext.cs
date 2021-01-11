using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace motivator.Models
{
    public partial class TargeterContext : DbContext
    {
        public TargeterContext()
        {
        }

        public TargeterContext(DbContextOptions<TargeterContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Motivation> Motivations { get; set; }
        public virtual DbSet<Schedule> Schedules { get; set; }
        public virtual DbSet<Target> Targets { get; set; }
        public virtual DbSet<TargetCategory> TargetCategories { get; set; }
        public virtual DbSet<TargeterUser> TargeterUsers { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                //optionsBuilder.UseNpgsql("Host=localhost;Uid=postgres;Pwd=postgres;Database=targeter");
                //production db configuration
                optionsBuilder.UseNpgsql("Host=104.197.25.5;Uid=postgres;Pwd=postgres;Database=targeter");

            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasPostgresEnum(null, "status", new[] { "ToDo", "InProgress", "Done" })
                .HasPostgresEnum(null, "week_day", new[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" })
                .HasAnnotation("Relational:Collation", "Russian_Russia.1251");

            modelBuilder.Entity<Motivation>(entity =>
            {
                entity.ToTable("motivation");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.TargetCategoryId).HasColumnName("target_category_id");

                entity.Property(e => e.Text)
                    .IsRequired()
                    .HasMaxLength(350)
                    .HasColumnName("text");

                entity.HasOne(d => d.TargetCategory)
                    .WithMany(p => p.Motivations)
                    .HasForeignKey(d => d.TargetCategoryId)
                    .HasConstraintName("motivation_target_category_id_fkey");
            });

            modelBuilder.Entity<Schedule>(entity =>
            {
                entity.ToTable("schedule");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.BeginTime)
                    .HasColumnName("begin_time")
                    .HasDefaultValueSql("CURRENT_TIMESTAMP");

                entity.Property(e => e.Cron)
                    .HasColumnType("character varying")
                    .HasColumnName("cron");

                entity.Property(e => e.Interval).HasColumnName("interval");

                entity.Property(e => e.WeekDaysTime)
                    .HasColumnType("time without time zone[]")
                    .HasColumnName("week_days_time");
            });

            modelBuilder.Entity<Target>(entity =>
            {
                entity.ToTable("target");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.PhotoReport).HasColumnName("photo_report");

                entity.Property(e => e.Schedule).HasColumnName("schedule");

                entity.Property(e => e.TargetCategoryId).HasColumnName("target_category_id");

                entity.Property(e => e.Text)
                    .IsRequired()
                    .HasMaxLength(250)
                    .HasColumnName("text");

                entity.Property(e => e.UserId).HasColumnName("user_id");

                entity.HasOne(d => d.ScheduleNavigation)
                    .WithMany(p => p.Targets)
                    .HasForeignKey(d => d.Schedule)
                    .HasConstraintName("target_schedule_fkey");

                entity.HasOne(d => d.TargetCategory)
                    .WithMany(p => p.Targets)
                    .HasForeignKey(d => d.TargetCategoryId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("target_target_category_id_fkey");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Targets)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("target_user_id_fkey");
            });

            modelBuilder.Entity<TargetCategory>(entity =>
            {
                entity.ToTable("target_category");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(150)
                    .HasColumnName("name");

                entity.Property(e => e.UserId).HasColumnName("user_id");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.TargetCategories)
                    .HasForeignKey(d => d.UserId)
                    .OnDelete(DeleteBehavior.Cascade)
                    .HasConstraintName("target_category_user_id_fkey");
            });

            modelBuilder.Entity<TargeterUser>(entity =>
            {
                entity.ToTable("targeter_user");

                entity.HasIndex(e => e.Password, "targeter_user_password_key")
                    .IsUnique();

                entity.HasIndex(e => e.Username, "targeter_user_username_key")
                    .IsUnique();

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name)
                    .HasMaxLength(15)
                    .HasColumnName("name");

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasColumnType("character varying")
                    .HasColumnName("password");

                entity.Property(e => e.Surname)
                    .HasMaxLength(15)
                    .HasColumnName("surname");

                entity.Property(e => e.Username)
                    .IsRequired()
                    .HasMaxLength(100)
                    .HasColumnName("username");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
