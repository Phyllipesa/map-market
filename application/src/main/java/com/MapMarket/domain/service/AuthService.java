package com.MapMarket.domain.service;

import com.MapMarket.application.rest.requestDto.AccountCredentialsDto;
import com.MapMarket.domain.models.Token;
import com.MapMarket.domain.exception.InvalidCredentialsException;
import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.input.AuthUseCase;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import com.MapMarket.domain.service.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public Token signIn(AccountCredentialsDto data) {
    try
    {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              data.getUsername(),
              data.getPassword()
          ));
    }
    catch (Exception e)
    {
      throw new InvalidCredentialsException(Constant.INVALID_USER_OR_PASSWORD);
    }

    User user = outputPort.findByUsername(data.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(Constant.USERNAME_NOT_FOUND + data.getUsername()));

    Token tokenResponse;
    tokenResponse = tokenProvider.createAccessToken(data.getUsername(), user.getRoles());
    return tokenResponse;
  }

  @Override
  public Token refreshToken(String username, String refreshToken) {
    outputPort.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Constant.USERNAME_NOT_FOUND + username));
    Token tokenResponse;
    tokenResponse = tokenProvider.refreshToken(refreshToken);
    return tokenResponse;
  }
}
