package com.MapMarket.application.rest.responseDto;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({ "id", "shelvingUnit", "side", "part", "shelf", "product"})
public class LocationResponseDto extends RepresentationModel<LocationResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long key;
  private Long shelvingUnit;
  private String side;
  private Long part;
  private Long shelf;
  private ProductResponseDto productResponseDto;

  public LocationResponseDto() {
  }

  public LocationResponseDto(Long id, ShelvingUnitEntity shelvingUnit, String side, Long part, Long shelf, ProductResponseDto productResponseDto) {
    this.key = id;
    this.shelvingUnit = shelvingUnit.getId();
    this.side = side;
    this.part = part;
    this.shelf = shelf;
    this.productResponseDto = productResponseDto;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public Long getShelvingUnit() {
    return shelvingUnit;
  }

  public void setShelvingUnit(Long shelvingUnit) {
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

  public ProductResponseDto getProduct() {
    return productResponseDto;
  }

  public void setProduct(ProductResponseDto productResponseDto) {
    this.productResponseDto = productResponseDto;
  }
}
