package com.kaisrtia.auth_service.DTO;

import com.kaisrtia.auth_service.Enum.Role;
import com.kaisrtia.auth_service.entity.User;

import java.util.Objects;

public class UserDTO {
  private long id;
  private String name;
  private String username;
  private Role role;

  public UserDTO() {
  }

  public UserDTO(User u) {
    this.id = u.getId();
    this.name = u.getName();
    this.username = u.getUsername();
    this.role = u.getRole();
  }

  public UserDTO(long id, String name, String username, Role role) {
    this.id = id;
    this.name = name;
    this.username = username;
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

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDTO user = (UserDTO) o;
    return id == user.id && Objects.equals(name, user.name)
        && Objects.equals(username, user.username)
        && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, username, role);
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", username='" + username + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
