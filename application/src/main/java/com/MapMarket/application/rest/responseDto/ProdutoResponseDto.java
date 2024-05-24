package com.MapMarket.application.rest.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({ "id", "nome", "preco" })
public class ProdutoResponseDto extends RepresentationModel<ProdutoResponseDto> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long key;
  private String nome;
  private Double preco;

  public ProdutoResponseDto() {
  }

  public ProdutoResponseDto(Long key, String nome, Double preco) {
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
}
