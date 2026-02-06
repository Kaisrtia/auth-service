package com.kaisrtia.auth_service.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  String id;

  String name;

  @Size(min = 3, max = 100, message = "INVALID_USERNAME")
  @Column(unique = true, nullable = false)
  String username;

  @Size(min = 6, max = 100, message = "INVALID_PASSWORD")
  @Column(nullable = false)
  String password;

  List<String> roles;
}
