package com.kaisrtia.auth_service.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaisrtia.auth_service.entity.InvalidatedToken;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
  @Modifying
  @Query("DELETE FROM InvalidatedToken it WHERE it.expiryTime < :date")
  void deleteByExpiryTimeBefore(LocalDateTime date);
}
