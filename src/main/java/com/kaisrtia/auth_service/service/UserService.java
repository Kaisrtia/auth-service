package com.kaisrtia.auth_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.kaisrtia.auth_service.repository.UserRepository;
import com.kaisrtia.auth_service.DTO.Response.UserResponse;
import com.kaisrtia.auth_service.DTO.Request.UserCreationRequest;
import com.kaisrtia.auth_service.entity.User;
import com.kaisrtia.auth_service.exception.AppExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse createUser(UserCreationRequest request) {
    // map request to user entity
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    // save user to database
    try {
      userRepository.save(user);
    } catch (Exception e) {
      throw new AppExceptionHandler("User already exists!", HttpStatus.BAD_REQUEST);
    }

    // map user entity to response
    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }

  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new AppExceptionHandler("User not found!", HttpStatus.NOT_FOUND);
    }
    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }

  public List<UserResponse> getUsers() {
    List<User> users = userRepository.findAll();
    List<UserResponse> userReposonses = new ArrayList<>();
    for(User u: users) {
      UserResponse urp = new UserResponse();
      urp.setUsername(u.getUsername());
      userReposonses.add(urp);  
    }
    return userReposonses;
  }

  public UserResponse updateUser(String username, UserCreationRequest request) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new AppExceptionHandler("User not found!", HttpStatus.NOT_FOUND);
    }
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }

  public UserResponse deleteUser(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new AppExceptionHandler("User not found!", HttpStatus.NOT_FOUND);
    }
    userRepository.delete(user);
    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }
}
