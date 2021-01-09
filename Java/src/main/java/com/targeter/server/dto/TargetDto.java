package com.targeter.server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TargetDto {
  private Long id;

  private Long userId;

  @NotNull
  @NotEmpty
  private String text;

  @NotNull
  private Long categoryId;

  @NotNull
  @NotEmpty
  private String status;
}
