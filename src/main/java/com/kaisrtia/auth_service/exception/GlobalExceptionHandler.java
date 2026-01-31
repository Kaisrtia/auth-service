package com.kaisrtia.auth_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // Order of exception isn't matter

  // Ancestor Exception
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAllExceptions(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.status(500).body("Unknow error!");
  }

}
