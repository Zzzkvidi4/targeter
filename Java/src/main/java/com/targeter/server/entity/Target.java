package com.targeter.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "target")
public class Target {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @NotNull Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private @NotNull User user;

  @Column(name = "text")
  private @NotNull String text;

  @ManyToOne
  @JoinColumn(name = "target_category_id")
  private @NotNull TargetCategory category;

  @Enumerated(EnumType.ORDINAL)
  private @NotNull Status status;

  @OneToOne
  @JoinColumn(name = "schedule")
  private @Nullable Schedule schedule;

  @Column(name = "photo_report")
  private @Nullable byte[] photoReport;
}
