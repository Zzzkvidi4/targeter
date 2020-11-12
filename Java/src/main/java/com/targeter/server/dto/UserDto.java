package com.targeter.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
  private Long id;

  private String username;

  private String password;

  private List<String> roles;

  private String accessToken;
}
