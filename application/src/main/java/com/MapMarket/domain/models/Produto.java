package com.MapMarket.domain.models;

import java.util.Objects;


public class Produto {
  private Long id;
  private String nome;
  private Double preco;

  public Produto() {
  }

  public Produto(String nome, Double preco) {
    this.nome = nome;
    this.preco = preco;
  }

  public Produto(Long id, String nome, Double preco) {
    this.id = id;
    this.nome = nome;
    this.preco = preco;
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

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Produto produto)) return false;
    return Objects.equals(id, produto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
