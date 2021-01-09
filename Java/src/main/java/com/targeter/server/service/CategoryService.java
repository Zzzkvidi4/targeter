package com.targeter.server.service;

import com.targeter.server.dto.CategoryDto;
import com.targeter.server.dto.Data;
import com.targeter.server.entity.TargetCategory;

import java.util.List;

public interface CategoryService {
  Data<List<CategoryDto>> findAll();

  Data<CategoryDto> create(CategoryDto category);
}
