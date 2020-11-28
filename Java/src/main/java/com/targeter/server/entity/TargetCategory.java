package com.targeter.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "target_category")
public class TargetCategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private @Nullable User user;

    @Column(name = "name")
    private @NotNull String name;
}
