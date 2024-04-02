package com.MapMarket.application.rest.responseDto;

public class ProdutoResponseDto {

  private Long id;
  private String nome;

  public ProdutoResponseDto() {
  }

  public ProdutoResponseDto(Long id, String nome) {
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
}
