package com.MapMarket.domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ShelvingUnit implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long unit;
  private String sideA;
  private String sideB;

  public ShelvingUnit() {
  }

  public ShelvingUnit(Long id, Long unit, String sideA, String sideB) {
    this.id = id;
    this.unit = unit;
    this.sideA = sideA;
    this.sideB = sideB;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ShelvingUnit that)) return false;
    return Objects.equals(id, that.id) && Objects.equals(unit, that.unit) && Objects.equals(sideA, that.sideA) && Objects.equals(sideB, that.sideB);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, unit, sideA, sideB);
  }
}
