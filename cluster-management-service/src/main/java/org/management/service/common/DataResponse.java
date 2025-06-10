package org.management.service.common;

import java.util.List;

public record DataResponse<T>(List<T> data) {

  public static <T> DataResponse<T> of(List<T> data) {
    return new DataResponse<>(data);
  }
}
