package com.targeter.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "motivation")
public class Motivation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private @NotNull String text;

    @ManyToOne
    @JoinColumn(name = "target_category_id")
    private @NotNull TargetCategory category;
}
