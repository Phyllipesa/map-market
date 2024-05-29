package com.MapMarket.application.rest.requestDto;

import java.io.Serial;
import java.io.Serializable;

public class ProductRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private String nome;
  private Double preco;

  public ProductRequestDto() {
  }

  public ProductRequestDto(String nome, Double preco) {
    this.nome = nome;
    this.preco = preco;
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
}
