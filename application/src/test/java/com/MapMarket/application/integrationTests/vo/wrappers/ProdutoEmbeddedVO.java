package com.MapMarket.application.integrationTests.vo.wrappers;

import com.MapMarket.application.integrationTests.vo.ProdutoResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProdutoEmbeddedVO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("produtoResponseDtoList")
  private List<ProdutoResponseDto> produtos;

  public ProdutoEmbeddedVO() {}

  public List<ProdutoResponseDto> getProdutos() {
    return produtos;
  }

  public void setProdutos(List<ProdutoResponseDto> produtos) {
    this.produtos = produtos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProdutoEmbeddedVO that)) return false;
    return Objects.equals(produtos, that.produtos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(produtos);
  }
}
