package com.MapMarket.application.rest.responseDto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

public class ProdutoResponseDto extends RepresentationModel<ProdutoResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String nome;
  private Double preco;

  public ProdutoResponseDto() {
  }

  public ProdutoResponseDto(Long id, String nome, Double preco) {
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
}
