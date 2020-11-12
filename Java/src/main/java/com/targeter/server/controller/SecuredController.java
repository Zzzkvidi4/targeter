package com.targeter.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecuredController {
  @GetMapping("/user")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public String helloSecured() {
    return "Hello from authorized";
  }

  @GetMapping("/all")
  public String hello() {
    return "Hello from not authorized";
  }
}
