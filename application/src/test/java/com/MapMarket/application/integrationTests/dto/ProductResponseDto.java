package com.MapMarket.application.integrationTests.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({ "id", "name", "price" })
public class ProductResponseDto extends RepresentationModel<ProductResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long key;
  private String name;
  private Double price;

  public ProductResponseDto() {
  }

  public ProductResponseDto(Long key, String name, Double price) {
    this.key = key;
    this.name = name;
    this.price = price;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
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
