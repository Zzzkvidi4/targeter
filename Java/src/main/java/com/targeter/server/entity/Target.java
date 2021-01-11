package com.targeter.server.entity;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "target")
@TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)
public class Target {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private @NotNull User user;

  @Column(name = "text")
  private @NotNull String text;

    @ManyToOne
    @JoinColumn(name = "target_category_id")
    private @NotNull TargetCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "status")
    @Type(type = "pgsql_enum")
    private @NotNull Status status;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule", referencedColumnName = "id")*/
    @OneToOne
    @JoinColumn(name = "schedule")
    private @Nullable Schedule schedule;

    @Column(name = "photo_report")
    private @Nullable byte[] photoReport;
}
