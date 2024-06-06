package com.MapMarket.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "shelving_units")
public class ShelvingUnitEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 10)
  private String name;

  public ShelvingUnitEntity() {
  }

  public ShelvingUnitEntity(Long id, String name) {
    this.id = id;
    this.name = name;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ShelvingUnitEntity shelvingUnitEntity)) return false;
    return Objects.equals(id, shelvingUnitEntity.id) && Objects.equals(name, shelvingUnitEntity.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
