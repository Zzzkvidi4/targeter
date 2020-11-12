package com.targeter.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tbtarget")
public class Target {
  @Id
  @Column(name = "target_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long targetId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "target_name")
  private String name;

  @Column(name = "target_description")
  private String description;
}
