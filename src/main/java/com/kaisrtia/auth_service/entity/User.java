package com.kaisrtia.auth_service.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;

import com.kaisrtia.auth_service.Enum.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // create getter, setter automatically
@NoArgsConstructor // create constructor automatically
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters!")
  private String name;

  @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters!")
  @Column(unique = true, nullable = false)
  private String username;

  @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters!")
  @Column(nullable = false)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  List<Role> userRoles;
}
