package com.MapMarket.domain.logic;

import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.domain.exception.NegativePriceException;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;

public class ProductValidator {

  public void validate(ProdutoRequestDto produtoRequestDto) {
    validateName(produtoRequestDto.getNome());
    validateNotNullPrice(produtoRequestDto.getPreco());
    checkNegativePrice(produtoRequestDto.getPreco());
  }

  public void validateName(String nome) {
    if (nome == null || nome.isEmpty() || nome.isBlank())
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("nome"));
  }

  public void checkNegativePrice(Double price) {
    if (Double.compare(price, Constant.D_2) <= Constant.INT)
      throw new NegativePriceException(Constant.NEGATIVE_NOT_ALLOWED);
  }

  public void validateNotNullPrice(Double price) {
    if (price == null)
      throw new ParameterNotFoundException(Constant.requiredParameterMessage("preco"));
  }
}
