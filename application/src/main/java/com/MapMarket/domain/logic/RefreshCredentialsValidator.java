package com.MapMarket.domain.logic;

import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class RefreshCredentialsValidator {

  public void checkIfParamsIsNotNull(String username, String refreshToken) {
     if(refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank())
       throw new ParameterNotFoundException(Constant.REQUIRED_PARAMETER + "username/refreshToken" + Constant.IS_NULL_OR_BLANK);
  }
}