package com.kaisrtia.auth_service.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionHandler extends RuntimeException {
  private final String message;
  private final HttpStatus httpStatus;

  public AppExceptionHandler(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
