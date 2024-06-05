package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.models.Product;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class MockProduct {

  public ProductEntity mockEntity() {
    return mockEntity(0);
  }

  public Product mockModel() {
    return mockModel(0);
  }

  public ProductRequestDto mockRequestDto() {
    return mockRequestDto(0);
  }

  public ProductResponseDto mockResponseDto() {
    return mockResponseDto(0);
  }

  public List<Product> mockModelList() {
    List<Product> products = new ArrayList<Product>();
    for (int i = 0; i < 14; i++) {
      products.add(mockModel(i));
    }
    return products;
  }

  public List<ProductResponseDto> mockVOList() {
    List<ProductResponseDto> products = new ArrayList<>();
    for (int i = 0; i < 14; i++) {
      products.add(mockResponseDto(i));
    }
    return products;
  }

  public ProductEntity mockEntity(Integer number) {
    ProductEntity product = new ProductEntity();
    product.setId(number.longValue());
    product.setName("Product Name Test" + number);
    product.setPrice(14.50);
    return product;
  }

  public Product mockModel(Integer number) {
    Product product = new Product();
    product.setId(number.longValue());
    product.setName("Product Name Test" + number);
    product.setPrice(14.50);
    return product;
  }

  public ProductRequestDto mockRequestDto(Integer number) {
    ProductRequestDto product = new ProductRequestDto();
    product.setName("Product Name Test" + number);
    product.setPrice(14.50);
    return product;
  }

  public ProductResponseDto mockResponseDto(Integer number) {
    ProductResponseDto product = new ProductResponseDto();
    product.setKey(number.longValue());
    product.setName("Product Name Test" + number);
    product.setPrice(14.50);
    return product;
  }
}
