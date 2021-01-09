package com.targeter.server.repository;

import com.targeter.server.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
  List<Target> findAllByUserUserId(Long userId);
}
