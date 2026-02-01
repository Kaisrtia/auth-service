package com.kaisrtia.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaisrtia.auth_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // JpaRepository have had all CRUD method already
  User findByUsername(String username);
}
