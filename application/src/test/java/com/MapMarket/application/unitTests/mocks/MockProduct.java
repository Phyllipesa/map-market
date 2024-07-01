package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.models.Product;

public class MockProduct {

  public Product mockModel() {
    return mockModel(1);
  }

  public ProductRequestDto mockRequestDto() {
    return mockRequestDto(1);
  }

  public ProductResponseDto mockResponseDto() {
    return mockResponseDto(1);
  }

  public Product mockModel(Integer number) {
    Product product = new Product();
    product.setId(number.longValue());
    product.setName("Product Name Test " + number);
    product.setPrice(14.50);
    return product;
  }

  public ProductRequestDto mockRequestDto(Integer number) {
    ProductRequestDto product = new ProductRequestDto();
    product.setName("Product Name Test " + number);
    product.setPrice(14.50);
    return product;
  }

  public ProductResponseDto mockResponseDto(Integer number) {
    ProductResponseDto product = new ProductResponseDto();
    product.setKey(number.longValue());
    product.setName("Product Name Test " + number);
    product.setPrice(14.50);
    return product;
  }
}
