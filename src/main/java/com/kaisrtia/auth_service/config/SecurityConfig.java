package com.kaisrtia.auth_service.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  public final String[] PUBLIC_POST_ENDPOINT = {
      "/users",
      "/login",
      "/introspect"
  };

  @Autowired
  private CustomJWTDecoder customJWTDecoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.authorizeHttpRequests(request -> request
        .requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINT).permitAll()
        // .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("SCOPE_ADMIN")
        .anyRequest()
        .authenticated());
    httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwtConfigurer -> jwtConfigurer
            .decoder(customJWTDecoder)));
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
