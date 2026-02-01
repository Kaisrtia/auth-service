package com.kaisrtia.auth_service.DTO;

import com.kaisrtia.auth_service.Enum.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDTO {
  private String name;
  private String username;
  private Role role;
}
