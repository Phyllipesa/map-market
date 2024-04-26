package com.MapMarket.domain.logic;

import com.MapMarket.domain.exception.NegativePriceException;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.ProdutoConstant;
import com.MapMarket.domain.models.Produto;

public class ValidationProduct {

  public void validate(Produto produto) {
    validateNotNull(produto);
    validateName(produto.getNome());
    validateNotNullPrice(produto.getPreco());
    checkNegativePrice(produto.getPreco());
  }

  public void validateNotNull(Produto produto) {
    if (produto == null)
      throw new ResourceNotFoundException(ProdutoConstant.NULL_NOT_ALLOWED);
  }

  public void validateName(String nome) {
    if (nome == null || nome.isEmpty() || nome.isBlank())
      throw new ParameterNotFoundException(ProdutoConstant.REQUIRED_PARAMETER + "nome" + ProdutoConstant.IS_NULL_OR_BLANK);
  }

  public void checkNegativePrice(Double price) {
    if (Double.compare(price, ProdutoConstant.D_2) <= ProdutoConstant.INT)
      throw new NegativePriceException(ProdutoConstant.NEGATIVE_NOT_ALLOWED);
  }

  public void validateNotNullPrice(Double price) {
    if (price == null)
      throw new ParameterNotFoundException(ProdutoConstant.REQUIRED_PARAMETER + "preco" + ProdutoConstant.IS_NULL_OR_BLANK);
  }
}
