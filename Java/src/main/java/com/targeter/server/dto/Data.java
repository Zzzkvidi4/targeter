package com.targeter.server.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Data<T> {
  private T data;

  private List<String> errors;

  public static <U> Data<U> ok(U data) {
    return new Data<>(data, Collections.emptyList());
  }

  public static <U> Data<U> error(List<String> errors) {
    return new Data<>(null, errors);
  }
}
