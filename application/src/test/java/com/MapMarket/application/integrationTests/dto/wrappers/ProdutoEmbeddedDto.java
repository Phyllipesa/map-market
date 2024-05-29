package com.MapMarket.application.integrationTests.dto.wrappers;

import com.MapMarket.application.integrationTests.dto.ProdutoResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProdutoEmbeddedDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("produtoResponseDtoList")
  private List<ProdutoResponseDto> produtos;

  public ProdutoEmbeddedDto() {}

  public List<ProdutoResponseDto> getProdutos() {
    return produtos;
  }

  public void setProdutos(List<ProdutoResponseDto> produtos) {
    this.produtos = produtos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProdutoEmbeddedDto that)) return false;
    return Objects.equals(produtos, that.produtos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(produtos);
  }
}
