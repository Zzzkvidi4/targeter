package com.targeter.server.repository;

import com.targeter.server.entity.TargetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<TargetCategory, Long> {
}
