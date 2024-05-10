package com.MapMarket.domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public class Produto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Long key;
  private String nome;
  private Double preco;

  public Produto() {
  }

  public Produto(String nome, Double preco) {
    this.nome = nome;
    this.preco = preco;
  }

  public Produto(Long key, String nome, Double preco) {
    this.key = key;
    this.nome = nome;
    this.preco = preco;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
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
    if (!super.equals(o)) return false;
    return Objects.equals(key, produto.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), key);
  }
}
