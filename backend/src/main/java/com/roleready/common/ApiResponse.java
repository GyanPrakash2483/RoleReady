package com.roleready.common;

import java.util.Map;

public record ApiResponse<T>(boolean ok, T data, String message) {
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, data, null);
  }

  public static ApiResponse<Map<String, Object>> error(String message) {
    return new ApiResponse<>(false, null, message);
  }
}
