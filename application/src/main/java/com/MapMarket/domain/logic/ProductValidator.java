package com.MapMarket.domain.logic;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.domain.exception.NegativePriceException;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class ProductValidator {

  public void validate(ProductRequestDto productRequestDto) {
    validateName(productRequestDto.getName());
    validateNotNullPrice(productRequestDto.getPrice());
    checkNegativePrice(productRequestDto.getPrice());
  }

  public void validateName(String nome) {
    if (nome == null || nome.isEmpty() || nome.isBlank())
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("name"));
  }

  public void checkNegativePrice(Double price) {
    if (Double.compare(price, Constant.D_2) <= Constant.INT)
      throw new NegativePriceException(Constant.NEGATIVE_NOT_ALLOWED);
  }

  public void validateNotNullPrice(Double price) {
    if (price == null)
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("price"));
  }
}
