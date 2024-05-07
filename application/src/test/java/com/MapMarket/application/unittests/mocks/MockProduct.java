package com.MapMarket.application.unittests.mocks;

import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;

import java.util.ArrayList;
import java.util.List;

public class MockProduct {

  public ProdutoEntity mockEntity() {
    return mockEntity(0);
  }

  public Produto mockModel() {
    return mockModel(0);
  }

  public ProdutoRequestDto mockRequestDto() {
    return mockRequestDto(0);
  }

  public ProdutoResponseDto mockResponseDto() {
    return mockResponseDto(0);
  }

  public List<Produto> mockModelList() {
    List<Produto> products = new ArrayList<Produto>();
    for (int i = 0; i < 14; i++) {
      products.add(mockModel(i));
    }
    return products;
  }

  public List<ProdutoResponseDto> mockVOList() {
    List<ProdutoResponseDto> products = new ArrayList<>();
    for (int i = 0; i < 14; i++) {
      products.add(mockResponseDto(i));
    }
    return products;
  }

  public ProdutoEntity mockEntity(Integer number) {
    ProdutoEntity product = new ProdutoEntity();
    product.setId(number.longValue());
    product.setNome("Product Name Test" + number);
    product.setPreco(14.50);
    return product;
  }

  public Produto mockModel(Integer number) {
    Produto product = new Produto();
    product.setId(number.longValue());
    product.setNome("Product Name Test" + number);
    product.setPreco(14.50);
    return product;
  }

  public ProdutoRequestDto mockRequestDto(Integer number) {
    ProdutoRequestDto product = new ProdutoRequestDto();
    product.setNome("Product Name Test" + number);
    product.setPreco(14.50);
    return product;
  }

  public ProdutoResponseDto mockResponseDto(Integer number) {
    ProdutoResponseDto product = new ProdutoResponseDto();
    product.setKey(number.longValue());
    product.setNome("Product Name Test" + number);
    product.setPreco(14.50);
    return product;
  }
}
