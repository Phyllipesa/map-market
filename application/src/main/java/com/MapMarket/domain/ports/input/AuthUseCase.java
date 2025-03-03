package com.MapMarket.domain.ports.input;

import com.MapMarket.application.rest.requestDto.AccountCredentialsDto;
import com.MapMarket.domain.models.Token;

public interface AuthUseCase {
  Token signIn(AccountCredentialsDto data);
  Token refreshToken(String username, String refreshToken);
}
