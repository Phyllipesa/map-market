package com.MapMarket.domain.logic;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class ShelvingValidator {

  public void validate(ShelvingRequestDto shelvingRequestDto) {
    validateUnit(shelvingRequestDto.getUnit());
    validateNotNullSideA(shelvingRequestDto.getSideA());
  }

  public void validateUnit(Long unit) {
    if (unit == null)
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("unit"));
  }

  public void validateNotNullSideA(String sideA) {
    if (sideA == null || sideA.isEmpty())
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("sideA"));
  }
}
