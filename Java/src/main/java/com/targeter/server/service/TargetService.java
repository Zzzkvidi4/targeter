package com.targeter.server.service;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.TargetDto;

import java.util.List;

public interface TargetService {
  Data<TargetDto> createTarget(TargetDto dto);

  Data<List<TargetDto>> getTargets();
}
