package com.MapMarket.application.integrationTests.dto.wrappers;

import com.MapMarket.application.integrationTests.dto.ShelvingResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ShelvingEmbeddedDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("shelvingResponseDtoList")
  private List<ShelvingResponseDto> shelvings;

  public ShelvingEmbeddedDto() {}

  public List<ShelvingResponseDto> getShelvings() {
    return shelvings;
  }

  public void setShelvings(List<ShelvingResponseDto> shelvings) {
    this.shelvings = shelvings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ShelvingEmbeddedDto that)) return false;
    return Objects.equals(shelvings, that.shelvings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shelvings);
  }
}
