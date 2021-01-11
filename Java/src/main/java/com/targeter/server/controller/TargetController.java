package com.targeter.server.controller;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.TargetDto;
import com.targeter.server.motivator.MotivatorHttpNotifier;
import com.targeter.server.service.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;
    private final MotivatorHttpNotifier motivatorHttpNotifier;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Data<TargetDto> createTarget(@Valid @RequestBody TargetDto dto) throws IOException {
        Data<TargetDto> target = targetService.createTarget(dto);
        String scheduleCron = target.getData().getCron();
        if (nonNull(scheduleCron) && !scheduleCron.isEmpty()) {
            motivatorHttpNotifier.updateSchedule(target.getData().getId());
        }
        return target;
    }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Data<List<TargetDto>> getTargets() {
    return targetService.getTargets();
  }
}
