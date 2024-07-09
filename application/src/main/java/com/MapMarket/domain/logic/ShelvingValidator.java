package com.MapMarket.domain.logic;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.domain.exception.NegativePriceException;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class ShelvingValidator {

  public void validate(ShelvingRequestDto shelvingRequestDto) {
    validateUnit(shelvingRequestDto.getUnit());
    checkNegativeUnit(shelvingRequestDto.getUnit());
    validateNotNullSideA(shelvingRequestDto.getSideA());
  }

  public void validateUnit(Long unit) {
    if (unit == null)
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("unit"));
  }

  public void checkNegativeUnit(Long price) {
    if (Double.compare(price, Constant.D_2) <= Constant.INT)
      throw new NegativePriceException(Constant.NEGATIVE_NOT_ALLOWED);
  }

  public void validateNotNullSideA(String sideA) {
    if (sideA == null || sideA.isEmpty())
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("sideA"));
  }
}
