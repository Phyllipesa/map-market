package com.MapMarket.application.rest.requestDto;

public class ProdutoRequestDto {

  private String nome;

  public ProdutoRequestDto() {
  }

  public ProdutoRequestDto(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
}
