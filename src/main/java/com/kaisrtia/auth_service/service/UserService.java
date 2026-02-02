package com.kaisrtia.auth_service.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.kaisrtia.auth_service.repository.UserRepository;
import com.kaisrtia.auth_service.DTO.Response.UserResponse;
import com.kaisrtia.auth_service.DTO.Request.UserCreationRequest;
import com.kaisrtia.auth_service.entity.User;
import com.kaisrtia.auth_service.exception.AppException;
import com.kaisrtia.auth_service.exception.ErrorCode;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  public UserResponse createUser(UserCreationRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    try {
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }

  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());
    return response;
  }

  public List<UserResponse> getUsers() {
    List<User> users = userRepository.findAll();

    List<UserResponse> userReposonses = new ArrayList<>();
    for (User u : users) {
      UserResponse urp = new UserResponse();
      urp.setUsername(u.getUsername());
      userReposonses.add(urp);
    }

    return userReposonses;
  }

  public UserResponse updateUser(String username, UserCreationRequest request) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
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
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
    userRepository.delete(user);

    UserResponse response = new UserResponse();
    response.setUsername(user.getUsername());

    return response;
  }
}
