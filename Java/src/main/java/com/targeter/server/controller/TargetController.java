package com.targeter.server.controller;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.TargetDto;
import com.targeter.server.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/targets")
@RequiredArgsConstructor
public class TargetController {

  private final TargetService targetService;

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Data<TargetDto> createTarget(@Valid @RequestBody TargetDto dto) {
    return targetService.createTarget(dto);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Data<List<TargetDto>> getTargets() {
    return targetService.getTargets();
  }
}
