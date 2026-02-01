package com.kaisrtia.auth_service.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
  ADMIN,
  HOST,
  STUDENT,
  GUEST;

  @Override
  public String getAuthority() {
    return this.name();
  }
}

