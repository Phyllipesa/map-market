package com.MapMarket.domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public class Product implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private Double price;

  public Product() {
  }

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product product)) return false;
    if (!super.equals(o)) return false;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
