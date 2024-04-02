package com.MapMarket.domain.models;

import java.util.Objects;

public class Lados {

  private Long id;

  private String lado;

  private Estantes estantes;

  public Lados(Long id, String lado, Estantes estantes) {
    this.id = id;
    this.lado = lado;
    this.estantes = estantes;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLado() {
    return lado;
  }

  public void setLado(String lado) {
    this.lado = lado;
  }

  public Estantes getEstantes() {
    return estantes;
  }

  public void setEstantes(Estantes estantes) {
    this.estantes = estantes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Lados lados)) return false;
    return Objects.equals(id, lados.id) && Objects.equals(lado, lados.lado) && Objects.equals(estantes, lados.estantes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lado, estantes);
  }
}
