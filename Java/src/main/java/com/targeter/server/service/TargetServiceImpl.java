package com.targeter.server.service;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.TargetDto;
import com.targeter.server.entity.Schedule;
import com.targeter.server.entity.Status;
import com.targeter.server.entity.Target;
import com.targeter.server.repository.CategoryRepository;
import com.targeter.server.repository.ScheduleRepository;
import com.targeter.server.repository.TargetRepository;
import com.targeter.server.repository.UserRepository;
import com.targeter.server.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    private final TargetRepository targetRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ScheduleRepository scheduleRepository;

    @Override
    public Data<TargetDto> createTarget(TargetDto dto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Target entity = new Target();
        entity.setText(dto.getText());
        entity.setCategory(categoryRepository.getOne(dto.getCategoryId()));
        entity.setStatus(Status.valueOf(dto.getStatus()));
        entity.setUser(userRepository.getOne(userDetails.getId()));
        if (nonNull(dto.getCron()) && !dto.getCron().isEmpty()) {
            Schedule schedule = new Schedule();
            schedule.setBeginTime(new Timestamp(System.currentTimeMillis()));
            schedule.setCron(dto.getCron());
            //schedule.setTarget(entity);
            scheduleRepository.save(schedule);
            entity.setSchedule(schedule);
        }
        return Data.ok(toDto(targetRepository.save(entity)));
    }

  @Override
  public Data<List<TargetDto>> getTargets() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Data.ok(
        targetRepository.findAllByUserUserId(userDetails.getId())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList())
    );
  }

  private TargetDto toDto(Target entity) {
    TargetDto dto = new TargetDto();
    dto.setCategoryId(entity.getCategory().getId());
    dto.setId(entity.getId());
    dto.setText(entity.getText());
    dto.setStatus(entity.getStatus().name());
      dto.setUserId(entity.getUser().getUserId());
      dto.setCron(entity.getSchedule() == null ? "" : entity.getSchedule().getCron());
    return dto;
  }
}
