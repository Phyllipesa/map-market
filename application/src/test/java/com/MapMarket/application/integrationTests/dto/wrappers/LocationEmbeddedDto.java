package com.MapMarket.application.integrationTests.dto.wrappers;

import com.MapMarket.application.integrationTests.dto.LocationResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class LocationEmbeddedDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @JsonProperty("locationResponseDtoList")
  private List<LocationResponseDto> locations;

  public LocationEmbeddedDto() {}

  public List<LocationResponseDto> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationResponseDto> locations) {
    this.locations = locations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LocationEmbeddedDto that)) return false;
    return Objects.equals(locations, that.locations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locations);
  }
}
