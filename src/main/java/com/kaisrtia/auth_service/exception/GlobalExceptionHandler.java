package com.kaisrtia.auth_service.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;

import com.kaisrtia.auth_service.DTO.Response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // Handle custom exception
  @ExceptionHandler(AppExceptionHandler.class)
  public ApiResponse<Void> handleAppExceptions(AppExceptionHandler ex) {
    return new ApiResponse<>(
        ex.getHttpStatus().value(),
        ex.getMessage(),
        null);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ApiResponse<Void> handleAccessDeniedException(AccessDeniedException ex) {
    return new ApiResponse<>(
        HttpStatus.FORBIDDEN.value(),
        "Access denied!",
        null);
  }

  // Catch all exceptions
  @ExceptionHandler(Exception.class)
  public ApiResponse<Void> handleAllExceptions(Exception ex) {
    ex.printStackTrace();
    return new ApiResponse<>(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Unknown error: " + ex.getMessage(),
        null);
  }
}
