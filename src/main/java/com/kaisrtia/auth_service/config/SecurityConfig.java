package com.kaisrtia.auth_service.config;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  public final String[] PUBLIC_POST_ENDPOINT = {
      "/users",
      "/login",
      "/introspect"
  };
  @Value("${jwt.signerKey}")
  private String SIGNER_KEY;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.authorizeHttpRequests(request -> request
        .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINT).permitAll()
        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("SCOPE_ADMIN")
        .anyRequest()
        .authenticated());
    httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwtConfigurer -> jwtConfigurer
            .decoder(jwtDecoder())));
    return httpSecurity.build();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(
        SIGNER_KEY.getBytes(), "HS512");
    return NimbusJwtDecoder
        .withSecretKey(secretKeySpec)
        .macAlgorithm(MacAlgorithm.HS512)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
