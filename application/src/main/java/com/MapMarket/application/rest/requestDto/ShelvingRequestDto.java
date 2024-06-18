package com.MapMarket.application.rest.requestDto;

import java.io.Serial;
import java.io.Serializable;

public class ShelvingRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long unit;
  private String sideA;
  private String sideB;

  public ShelvingRequestDto() {
  }

  public ShelvingRequestDto(Long unit, String sideA, String sideB) {
    this.unit = unit;
    this.sideA = sideA;
    this.sideB = sideB;
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