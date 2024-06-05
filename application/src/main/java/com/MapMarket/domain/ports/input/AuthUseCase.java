package com.MapMarket.domain.ports.input;

import com.MapMarket.application.rest.requestDto.AccountCredentialsDto;
import com.MapMarket.application.rest.responseDto.TokenDto;

public interface AuthUseCase {
  TokenDto signIn(AccountCredentialsDto data);
  TokenDto refreshToken(String username, String refreshToken);
}
