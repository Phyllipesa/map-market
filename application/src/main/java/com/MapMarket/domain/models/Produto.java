package com.MapMarket.domain.models;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;

import java.util.Objects;


public class Produto {
  private Long id;
  private String nome;

  public Produto() {
  }

  public Produto(String nome) {
    this.nome = nome;
  }

  public Produto(Long id, String nome) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Produto produto)) return false;
    return Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome);
  }
}
