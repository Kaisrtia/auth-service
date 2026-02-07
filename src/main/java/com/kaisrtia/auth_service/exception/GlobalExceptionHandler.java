package com.kaisrtia.auth_service.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

import com.kaisrtia.auth_service.DTO.Response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // Handle custom exceptions
  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiResponse> handleAppException(AppException ex) {
    ErrorCode errorCode = ex.getCode();

    ApiResponse apiResponse = new ApiResponse<>();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(ex.getMessage());

    return ResponseEntity
        .status(errorCode.getStatusCode())
        .body(apiResponse);
  }

  // Handle validation exceptions
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {
    String enumKey = ex.getFieldError().getDefaultMessage();
    System.out.println(enumKey);

    ErrorCode errorCode = ErrorCode.INVALID_KEY;
    try {
      errorCode = ErrorCode.valueOf(enumKey);
    } catch (IllegalArgumentException e) {

    }

    ApiResponse apiResponse = new ApiResponse<>();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());

    return ResponseEntity
        .status(errorCode.getStatusCode())
        .body(apiResponse);
  }

  // Catch all exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleException(Exception ex) {
    ApiResponse apiResponse = new ApiResponse<>();
    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    return ResponseEntity
        .status(ErrorCode.UNAUTHENTICATED.getStatusCode())
        .body(apiResponse);
  }
}
