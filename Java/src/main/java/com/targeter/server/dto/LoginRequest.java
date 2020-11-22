package com.targeter.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String username;

  private String password;

  private String method;

  private String code;
}
