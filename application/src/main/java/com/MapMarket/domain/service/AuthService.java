package com.MapMarket.domain.service;

import com.MapMarket.application.rest.requestDto.AccountCredentialsVO;
import com.MapMarket.application.rest.responseDto.TokenVO;
import com.MapMarket.domain.exception.InvalidCredentialsException;
import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.input.AuthUseCase;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import com.MapMarket.domain.service.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

  private final FindByUserNameOutputPort<User> outputPort;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  public AuthService(FindByUserNameOutputPort<User> outputPort, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
    this.outputPort = outputPort;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public TokenVO signIn(AccountCredentialsVO data) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            data.getUsername(),
            data.getPassword()
        ));

    User user = outputPort.findByUsername(data.getUsername())
        .orElseThrow(() -> new InvalidCredentialsException("Invalid username/password supplied!"));

    TokenVO tokenResponse;
    tokenResponse = tokenProvider.createAccessToken(data.getUsername(), user.getRoles());
    return tokenResponse;
  }

  @Override
  public TokenVO refreshToken(String username, String refreshToken) {
    return null;
  }
}
