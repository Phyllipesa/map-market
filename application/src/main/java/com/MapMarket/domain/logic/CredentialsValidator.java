package com.MapMarket.domain.logic;

import com.MapMarket.application.rest.requestDto.AccountCredentialsVO;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class CredentialsValidator {

  public void checkIfParamsIsNotNull(AccountCredentialsVO data) {
    if (data == null || data.getUsername() == null || data.getUsername().isBlank()
        || data.getPassword() == null || data.getPassword().isBlank())
      throw new ParameterNotFoundException(Constant.requiredCredentialsMessage("username/password"));
  }
}
