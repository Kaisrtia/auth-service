package com.kaisrtia.auth_service.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaisrtia.auth_service.DTO.Request.AuthenticationRequest;
import com.kaisrtia.auth_service.DTO.Request.IntrospectRequest;
import com.kaisrtia.auth_service.DTO.Request.RefreshRequest;
import com.kaisrtia.auth_service.DTO.Response.ApiResponse;
import com.kaisrtia.auth_service.DTO.Response.AuthenticationResponse;
import com.kaisrtia.auth_service.DTO.Response.IntrospectResponse;
import com.kaisrtia.auth_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
  AuthenticationService authenticationService;

  @PostMapping("/login")
  public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    var result = authenticationService.authenticate(request);
    return ApiResponse.<AuthenticationResponse>builder()
        .result(result)
        .build();
  }

  @PostMapping("/introspect")
  public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
      throws JOSEException, ParseException {
    var result = authenticationService.introspect(request);
    return ApiResponse.<IntrospectResponse>builder()
        .result(result)
        .build();
  }

  @PostMapping("/auth/refresh")
  public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request) {
    var result = authenticationService.refreshToken(request.getRefreshToken());
    return ApiResponse.<AuthenticationResponse>builder()
        .result(result)
        .build();
  }
}
