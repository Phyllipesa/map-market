package com.MapMarket.domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private Long shelvingUnitId;
  private String side;
  private Long part;
  private Long shelf;
  private Product product;

  public Location() {
  }

  public Location(Long id, Long shelvingUnitId, String side, Long part, Long shelf, Product product) {
    this.id = id;
    this.shelvingUnitId = shelvingUnitId;
    this.side = side;
    this.part = part;
    this.shelf = shelf;
    this.product = product;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getShelvingUnitId() {
    return shelvingUnitId;
  }

  public void setShelvingUnitId(Long shelvingUnitId) {
    this.shelvingUnitId = shelvingUnitId;
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Location location)) return false;
    return Objects.equals(id, location.id) && Objects.equals(shelvingUnitId, location.shelvingUnitId) && Objects.equals(side, location.side) && Objects.equals(part, location.part) && Objects.equals(shelf, location.shelf) && Objects.equals(product, location.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, shelvingUnitId, side, part, shelf, product);
  }
}
