package com.MapMarket.application.integrationTests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class WrapperProductResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("_embedded")
  private ProductEmbeddedDto embedded;

  public WrapperProductResponseDto() {}

  public ProductEmbeddedDto getEmbedded() {
    return embedded;
  }

  public void setEmbedded(ProductEmbeddedDto embedded) {
    this.embedded = embedded;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WrapperProductResponseDto that)) return false;
    return Objects.equals(embedded, that.embedded);
  }

  @Override
  public int hashCode() {
    return Objects.hash(embedded);
  }
}
