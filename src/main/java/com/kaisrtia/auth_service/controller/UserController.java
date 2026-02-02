package com.kaisrtia.auth_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.kaisrtia.auth_service.service.UserService;
import com.kaisrtia.auth_service.DTO.Request.UserCreationRequest;
import com.kaisrtia.auth_service.DTO.Response.UserResponse;
import com.kaisrtia.auth_service.entity.User;
import com.kaisrtia.auth_service.DTO.Response.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserService userService;

  @PostMapping
  public ApiResponse<UserResponse> createUser(
      @RequestBody @Valid UserCreationRequest request) {
    return new ApiResponse<UserResponse>(200,
        "User created successfully!",
        userService.createUser(request));
  }

  @GetMapping
  public ApiResponse<List<UserResponse>> getUsers() {
    return new ApiResponse<List<UserResponse>>(200,
        "Users retrieved successfully!",
        userService.getUsers());
  }

  @GetMapping("/{username}")
  public ApiResponse<UserResponse> getUserByUsername(@PathVariable String username) {
    return new ApiResponse<UserResponse>(200,
        "User retrieved successfully!",
        userService.getUserByUsername(username));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<String> deleteUserByUsername(@PathVariable String Id) {
    userService.deleteUser(Id);
    return ApiResponse.<String>builder()
      .result("User has been deleted")
      .build();
  }
}
