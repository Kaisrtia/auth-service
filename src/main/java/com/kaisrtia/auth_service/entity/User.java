package com.kaisrtia.auth_service.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import com.kaisrtia.auth_service.Enum.Role;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private long id;
  @NotEmpty(message = "Name must not be empty")
  private String name;
  @NotEmpty(message = "Username must not be empty!")
  private String username;
  @NotEmpty(message = "Password must not be empty!")
  @Length(min = 5, message = "Password must be at least 5 characters!")
  private String password;
  private Role role;

  public User() {
  }

  public User(String name, String username, String password, Role role) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return id == user.id && Objects.equals(name, user.name)
        && Objects.equals(username, user.username)
        && Objects.equals(password, user.password)
        && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, username, password, role);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
