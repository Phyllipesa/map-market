package com.MapMarket.application.rest.requestDto;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;

import java.io.Serial;
import java.io.Serializable;

public class LocationRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private ShelvingUnitEntity shelvingUnit;
  private String side;
  private Long part;
  private Long shelf;
  private Long productId;

  public LocationRequestDto() {
  }

  public LocationRequestDto(ShelvingUnitEntity shelvingUnit, String side, Long part, Long shelf, Long productId) {
    this.shelvingUnit = shelvingUnit;
    this.side = side;
    this.part = part;
    this.shelf = shelf;
    this.productId = productId;
  }

  public ShelvingUnitEntity getShelvingUnit() {
    return shelvingUnit;
  }

  public void setShelvingUnit(ShelvingUnitEntity shelvingUnit) {
    this.shelvingUnit = shelvingUnit;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public Long getPart() {
    return part;
  }

  public void setPart(Long part) {
    this.part = part;
  }

  public Long getShelf() {
    return shelf;
  }

  public void setShelf(Long shelf) {
    this.shelf = shelf;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
}
