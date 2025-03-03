package com.MapMarket.domain.service.security.config;

import com.MapMarket.domain.service.security.jwt.JwtTokenFilter;
import com.MapMarket.domain.service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Bean
  PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();

    Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    encoders.put("pbkdf2", pbkdf2Encoder);
    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
    return passwordEncoder;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

    return http
        .httpBasic(basic -> basic.disable())
        .csrf(csrf -> csrf.disable())
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                    "/auth/sign-in",
                    "/auth/refresh/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**").permitAll()
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/location",
                    "/api/v1/location/**",
                    "/api/v1/product",
                    "/api/v1/product/**",
                    "/api/v1/shelvingUnit",
                    "/api/v1/shelvingUnit/**").hasAnyRole("COMMON_USER", "MANAGER", "ADMIN")
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/v1/location/{locationId}/{productId}",
                    "/api/v1/location/{id}").hasRole("ADMIN")
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/product",
                    "/api/v1/shelvingUnit").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/v1/product/{id}",
                    "/api/v1/shelvingUnit/{id}").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/v1/product/{id}",
                    "/api/v1/shelvingUnit/{id}").hasRole("ADMIN")
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/users").denyAll()
        )
        .cors(cors -> {})
        .build();
  }
}
