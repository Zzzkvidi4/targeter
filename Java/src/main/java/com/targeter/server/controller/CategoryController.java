package com.targeter.server.controller;

import com.targeter.server.dto.CategoryDto;
import com.targeter.server.dto.Data;
import com.targeter.server.service.CategoryService;
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
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/all")
  public Data<List<CategoryDto>> getCategories() {
    return categoryService.findAll();
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Data<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category) {
    return categoryService.create(category);
  }
}
