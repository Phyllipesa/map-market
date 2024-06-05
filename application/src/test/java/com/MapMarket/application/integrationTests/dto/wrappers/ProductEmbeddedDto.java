package com.MapMarket.application.integrationTests.dto.wrappers;

import com.MapMarket.application.integrationTests.dto.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProductEmbeddedDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("productResponseDtoList")
  private List<ProductResponseDto> products;

  public ProductEmbeddedDto() {}

  public List<ProductResponseDto> getProducts() {
    return products;
  }

  public void setProducts(List<ProductResponseDto> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductEmbeddedDto that)) return false;
    return Objects.equals(products, that.products);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products);
  }
}
