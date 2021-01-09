package com.targeter.server.service;

import com.targeter.server.dto.CategoryDto;
import com.targeter.server.dto.Data;
import com.targeter.server.entity.TargetCategory;
import com.targeter.server.repository.CategoryRepository;
import com.targeter.server.repository.UserRepository;
import com.targeter.server.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final UserRepository userRepository;

  @Override
  public Data<List<CategoryDto>> findAll() {
    return Data.ok(
        categoryRepository.findAll()
            .stream()
            .map(this::map)
            .collect(toList())
    );
  }

  @Override
  public Data<CategoryDto> create(CategoryDto dto) {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    TargetCategory category = new TargetCategory();
    category.setName(dto.getName());
    category.setUser(userRepository.getOne(userDetails.getId()));
    return Data.ok(map(categoryRepository.save(category)));
  }

  private CategoryDto map(TargetCategory category) {
    return new CategoryDto(category.getId(), category.getName());
  }
}
