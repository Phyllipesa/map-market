package com.MapMarket.application.integrationTests.dto;

import java.io.Serial;
import java.io.Serializable;

public class ProductRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private String name;
  private Double price;

  public ProductRequestDto() {
  }

  public ProductRequestDto(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
