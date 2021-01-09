package com.targeter.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CategoryDto {

  private Long id;

  @NotNull
  @NotEmpty
  private String name;
}
