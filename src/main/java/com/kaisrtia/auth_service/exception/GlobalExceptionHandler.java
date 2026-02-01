package com.kaisrtia.auth_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // Handle custom exception
  @ExceptionHandler(AppExceptionHandler.class)
  public ResponseEntity<String> handleAppExceptions(AppExceptionHandler ex) {
    return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied!");
  }

  // Catch all exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknow error!");
  }
}
