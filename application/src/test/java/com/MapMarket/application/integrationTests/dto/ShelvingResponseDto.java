package com.MapMarket.application.integrationTests.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({ "id", "unit", "sideA", "sideB" })
public class ShelvingResponseDto extends RepresentationModel<com.MapMarket.application.rest.responseDto.ShelvingResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long key;
  private Long unit;
  private String sideA;
  private String sideB;

  public ShelvingResponseDto() {
  }

  public ShelvingResponseDto(Long unit, String sideA, String sideB) {
    this.unit = unit;
    this.sideA = sideA;
    this.sideB = sideB;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public Long getUnit() {
    return unit;
  }

  public void setUnit(Long unit) {
    this.unit = unit;
  }

  public String getSideA() {
    return sideA;
  }

  public void setSideA(String sideA) {
    this.sideA = sideA;
  }

  public String getSideB() {
    return sideB;
  }

  public void setSideB(String sideB) {
    this.sideB = sideB;
  }
}
