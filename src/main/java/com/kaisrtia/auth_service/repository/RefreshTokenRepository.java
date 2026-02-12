package com.kaisrtia.auth_service.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kaisrtia.auth_service.entity.RefreshToken;
import com.kaisrtia.auth_service.entity.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
  Optional<RefreshToken> findByToken(String token);

  Optional<RefreshToken> findByUser(User user);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :date")
  void deleteByExpiryDateBefore(LocalDateTime date);

  @Modifying
  @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
  void deleteByUser(User user);
}
