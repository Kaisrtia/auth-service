package com.kaisrtia.auth_service.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaisrtia.auth_service.DTO.Request.AuthenticationRequest;
import com.kaisrtia.auth_service.DTO.Request.IntrospectRequest;
import com.kaisrtia.auth_service.DTO.Response.AuthenticationResponse;
import com.kaisrtia.auth_service.DTO.Response.IntrospectResponse;
import com.kaisrtia.auth_service.entity.RefreshToken;
import com.kaisrtia.auth_service.exception.AppException;
import com.kaisrtia.auth_service.exception.ErrorCode;
import com.kaisrtia.auth_service.repository.RefreshTokenRepository;
import com.kaisrtia.auth_service.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.var;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import com.kaisrtia.auth_service.entity.User;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  RefreshTokenRepository refreshTokenRepository;

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;

  @NonFinal
  @Value("${jwt.access-token-expiration}")
  protected long ACCESS_TOKEN_EXPIRATION;

  @NonFinal
  @Value("${jwt.refresh-token-expiration}")
  protected long REFRESH_TOKEN_EXPIRATION;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    var user = userRepository.findByUsername(request.getUsername());
    if (user == null) {
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    boolean authenticated = passwordEncoder.matches(request.getPassword(),
        user.getPassword());
    if (!authenticated) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    String accessToken = generateAccessToken(user);
    String refreshToken = generateRefreshToken(user);

    return AuthenticationResponse.builder()
        .token(accessToken)
        .refreshToken(refreshToken)
        .authenticated(true)
        .build();
  }

  public IntrospectResponse introspect(IntrospectRequest request)
      throws JOSEException, ParseException {
    var token = request.getToken();

    JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    return IntrospectResponse.builder()
        .valid(signedJWT.verify(jwsVerifier) && expiryTime.after(new Date()))
        .build();
  }

  private String generateAccessToken(User user) {
    JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(user.getUsername())
        .issuer("Hoang Trung Dep Trai S1 TG")
        .issueTime(new Date())
        .expirationTime(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
        .claim("scope", buildScope(user))
        .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(jwsHeader, payload);

    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      // log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  private String generateRefreshToken(User user) {
    // Delete any existing refresh token for this user
    refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);

    // Generate a unique refresh token
    String tokenValue = UUID.randomUUID().toString();

    // Create and save refresh token entity
    RefreshToken refreshToken = RefreshToken.builder()
        .token(tokenValue)
        .user(user)
        .expiryDate(LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION / 1000))
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();

    refreshTokenRepository.save(refreshToken);
    return tokenValue;
  }

  public AuthenticationResponse refreshToken(String refreshTokenValue) {
    // Find refresh token in database
    RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
        .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

    // Check if token is revoked
    if (refreshToken.isRevoked()) {
      throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    // Check if token is expired
    if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }

    // Update the refresh token's last used time
    refreshToken.setUpdatedAt(LocalDateTime.now());
    refreshTokenRepository.save(refreshToken);

    // Generate new access token
    String newAccessToken = generateAccessToken(refreshToken.getUser());

    return AuthenticationResponse.builder()
        .token(newAccessToken)
        .refreshToken(refreshTokenValue)
        .authenticated(true)
        .build();
  }

  private String buildScope(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!user.getRoles().isEmpty()) {
      user.getRoles().forEach(token -> stringJoiner.add(token));
    }
    return stringJoiner.toString();
  }
}
