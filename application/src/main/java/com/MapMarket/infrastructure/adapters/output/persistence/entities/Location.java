package com.MapMarket.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "shelving_unit_id")
  private Long shelvingUnitId;
  private String side;
  private Long part;
  private Long shelf;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Long productId;

  public Location() {
  }

  public Location(Long id, Long shelvingUnitId, String side, Long part, Long shelf, Long productId) {
    this.id = id;
    this.shelvingUnitId = shelvingUnitId;
    this.side = side;
    this.part = part;
    this.shelf = shelf;
    this.productId = productId;
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

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Location location)) return false;
    return Objects.equals(id, location.id)
        && Objects.equals(shelvingUnitId, location.shelvingUnitId)
        && Objects.equals(side, location.side)
        && Objects.equals(part, location.part)
        && Objects.equals(shelf, location.shelf)
        && Objects.equals(productId, location.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, shelvingUnitId, side, part, shelf, productId);
  }
}
