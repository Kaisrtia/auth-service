package com.kaisrtia.auth_service.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
  private int code;
  private String message;
  private T result;

  public ApiResponse(int code, String message, T result) {
    this.code = code;
    this.message = message;
    this.result = result;
  }
}
