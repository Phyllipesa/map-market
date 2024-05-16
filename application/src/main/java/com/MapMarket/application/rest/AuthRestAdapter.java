package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.AccountCredentialsVO;
import com.MapMarket.application.rest.responseDto.TokenVO;
import com.MapMarket.domain.logic.CredentialsValidator;
import com.MapMarket.domain.ports.input.AuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthRestAdapter {

  private final AuthUseCase authUseCase;
  private final CredentialsValidator credentialsValidator;

  public AuthRestAdapter(AuthUseCase authUseCase, CredentialsValidator credentialsValidator) {
    this.authUseCase = authUseCase;
    this.credentialsValidator = credentialsValidator;
  }

  @PostMapping("/signin")
  public ResponseEntity<TokenVO> signin(@RequestBody AccountCredentialsVO data) {
    credentialsValidator.checkIfParamsIsNotNull(data);
    TokenVO token = authUseCase.signIn(data);
    return ResponseEntity.ok(token);
  }
}
