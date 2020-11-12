package com.targeter.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
  private String username;

  private String password;
}
