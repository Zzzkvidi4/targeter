package com.targeter.server.controller;

import com.targeter.server.dto.SignupRequest;
import com.targeter.server.dto.UserDto;
import com.targeter.server.dto.Data;
import com.targeter.server.dto.LoginRequest;
import com.targeter.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signin")
  public Data<UserDto> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.signIn(loginRequest);
  }

  @PostMapping("/signup")
  public Data<UserDto> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return userService.signUp(signUpRequest);
  }
}
