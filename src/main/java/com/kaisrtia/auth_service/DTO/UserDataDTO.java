package com.kaisrtia.auth_service.DTO;

import com.kaisrtia.auth_service.Enum.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {
  private String name;
  private String username;
  private String password;
  private Role role;
}
