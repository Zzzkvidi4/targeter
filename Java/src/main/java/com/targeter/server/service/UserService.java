package com.targeter.server.service;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.SignupRequest;
import com.targeter.server.dto.UserDto;
import com.targeter.server.dto.LoginRequest;

public interface UserService {
  Data<UserDto> signIn(LoginRequest loginRequest);

  Data<UserDto> signUp(SignupRequest signupRequest);
}
