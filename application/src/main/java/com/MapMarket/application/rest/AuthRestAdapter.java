package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.AccountCredentialsDto;
import com.MapMarket.application.rest.responseDto.TokenDto;
import com.MapMarket.domain.logic.CredentialsValidator;
import com.MapMarket.domain.logic.RefreshCredentialsValidator;
import com.MapMarket.domain.ports.input.AuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRestAdapter {

  private final AuthUseCase authUseCase;
  private final CredentialsValidator credentialsValidator;
  private final RefreshCredentialsValidator refreshCredentialsValidator;

  public AuthRestAdapter(AuthUseCase authUseCase, CredentialsValidator credentialsValidator, RefreshCredentialsValidator refreshCredentialsValidator) {
    this.authUseCase = authUseCase;
    this.credentialsValidator = credentialsValidator;
    this.refreshCredentialsValidator = refreshCredentialsValidator;
  }

  @PostMapping("/sign-in")
  public ResponseEntity<TokenDto> signIn(@RequestBody AccountCredentialsDto data) {
    credentialsValidator.checkIfParamsIsNotNull(data);
    TokenDto token = authUseCase.signIn(data);
    return ResponseEntity.ok(token);
  }

  @PutMapping("/refresh/{username}")
  public ResponseEntity<TokenDto> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
    refreshCredentialsValidator.checkIfParamsIsNotNull(username, refreshToken);
    TokenDto token = authUseCase.refreshToken(username, refreshToken);
    return ResponseEntity.ok(token);
  }
}
