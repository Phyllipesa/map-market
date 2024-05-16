package com.MapMarket.domain.ports.input;

import com.MapMarket.application.rest.requestDto.AccountCredentialsVO;
import com.MapMarket.application.rest.responseDto.TokenVO;

public interface AuthUseCase {
  TokenVO signIn(AccountCredentialsVO data);
  TokenVO refreshToken(String username, String refreshToken);
}
