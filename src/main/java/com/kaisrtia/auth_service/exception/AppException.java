package com.kaisrtia.auth_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

public class AppException extends RuntimeException {
  private ErrorCode errorCode;

  public AppException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getCode() {
    return errorCode;
  }

  public void setCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
