package com.MapMarket.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estantes {

  private Long id;
  private String name;
  private List<Lados> lados = new ArrayList<>();

  public Estantes() {
  }

  public Estantes(Long id, String name) {
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

  public List<Lados> getLados() {
    return lados;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Estantes estantes)) return false;
    return Objects.equals(id, estantes.id) && Objects.equals(name, estantes.name) && Objects.equals(lados, estantes.lados);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, lados);
  }
}
