package com.MapMarket.application.rest.responseDto;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

public class LocationResponseDto extends RepresentationModel<LocationResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long key;
  private ShelvingUnitEntity shelvingUnit;
  private String side;
  private Long part;
  private Long shelf;
  private ProductEntity product;

  public LocationResponseDto() {
  }

  public LocationResponseDto(Long id, ShelvingUnitEntity shelvingUnit, String side, Long part, Long shelf, ProductEntity product) {
    this.key = id;
    this.shelvingUnit = shelvingUnit;
    this.side = side;
    this.part = part;
    this.shelf = shelf;
    this.product = product;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
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

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }
}
