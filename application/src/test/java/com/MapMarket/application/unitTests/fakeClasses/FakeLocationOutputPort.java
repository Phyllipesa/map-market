package com.MapMarket.application.unitTests.fakeClasses;

import com.MapMarket.application.unitTests.mocks.MockLocation;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.ports.output.LocationOutputPort;

import java.util.Optional;

public class FakeLocationOutputPort implements LocationOutputPort<Location> {

  MockLocation mockLocation = new MockLocation();
  Location location = mockLocation.mockModel();

  @Override
  public Optional<Location> findById(Long id) {
    if (id == 2) {
      return Optional.of(location);
    } else if (id == 4) {
      return Optional.of(mockLocation.mockModelWithoutProduct(2L));
    }
    return Optional.empty();
  }

  @Override
  public Optional<Location> findLocationByProductId(Long id) {
    if (id == 2) {
      return Optional.of(location);
    }
    return Optional.empty();
  }

  @Override
  public Location subscribingProduct(Location newlocation) {
    return newlocation;
  }

  @Override
  public Location unsubscribingProduct(Long id) {
    Location model = mockLocation.mockModel();
    model.setProduct(null);
    return model;
  }

  @Override
  public boolean existResource(Long id) {
    return id == 1;
  }

  @Override
  public boolean existLocationWithProduct(Long id) {
    return id == 1;
  }

  @Override
  public boolean existProductInLocation(Long id) {
    return id == 1;
  }
}
