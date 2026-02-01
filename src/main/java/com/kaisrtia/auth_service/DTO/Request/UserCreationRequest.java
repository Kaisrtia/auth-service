package com.kaisrtia.auth_service.DTO.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreationRequest {
  private String username;
  private String password;
}
