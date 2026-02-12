package com.kaisrtia.auth_service.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.kaisrtia.auth_service.DTO.Request.IntrospectRequest;
import com.kaisrtia.auth_service.service.AuthenticationService;

@Component
public class CustomJWTDecoder implements JwtDecoder {
  @Value("${jwt.signerKey}")
  private String signerKey;

  @Autowired
  private AuthenticationService authenticationService;

  private NimbusJwtDecoder nimbusJwtDecoder;

  @Override
  public Jwt decode(String token) {
    try {
      var response = authenticationService.introspect(IntrospectRequest
        .builder()
        .token(token)
        .build()
      );

      if(!response.isValid()) {
        throw new JwtException("Token invalid");
      }
    } catch (Exception e) {
      throw new JwtException(e.getMessage());
    }

    if (nimbusJwtDecoder == null) {
      SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
      this.nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
        .macAlgorithm(MacAlgorithm.HS512)
        .build();
    }

    return nimbusJwtDecoder.decode(token);
  }
}
