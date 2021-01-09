package com.targeter.server.controller;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.StatusDto;
import com.targeter.server.entity.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/statuses")
public class StatusController {

  @GetMapping
  public Data<List<StatusDto>> getStatuses() {
    return Data.ok(
        Arrays.stream(Status.values())
            .map(s -> new StatusDto(s.name(), s.getDisplayName()))
            .collect(Collectors.toList())
    );
  }
}
