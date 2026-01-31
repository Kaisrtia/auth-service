package com.kaisrtia.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kaisrtia.auth_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  // JpaRepository have had all CRUD method already
}
