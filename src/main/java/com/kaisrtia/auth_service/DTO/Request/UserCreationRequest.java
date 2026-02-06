package com.kaisrtia.auth_service.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
  String name;

  @Size(min = 3, max = 100, message = "USERNAME_INVALID")
  String username;

  @Size(min = 6, max = 100, message = "INVALID_PASSWORD")
  String password;
}
