package com.MapMarket.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Estantes {

  private Long id;
  private String nome;
  private List<Lados> lados = new ArrayList<>();

  public Estantes() {
  }

  public Estantes(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<Lados> getLados() {
    return lados;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Estantes estantes)) return false;
    return Objects.equals(id, estantes.id) && Objects.equals(nome, estantes.nome) && Objects.equals(lados, estantes.lados);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome, lados);
  }
}
