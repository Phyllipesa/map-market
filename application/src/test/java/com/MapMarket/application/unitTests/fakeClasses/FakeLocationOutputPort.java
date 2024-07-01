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
    if (id == 1) {
      return Optional.of(location);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Location> findLocationByProductId(Long id) {
    if (id == 1) {
      return Optional.of(location);
    }
    return Optional.empty();
  }

  @Override
  public Location subscribingProduct(Location newlocation) {
    location.setId(newlocation.getId());
    location.setShelvingUnitId(newlocation.getShelvingUnitId());
    location.setSide(newlocation.getSide());
    location.setPart(newlocation.getPart());
    location.setShelf(newlocation.getShelf());
    location.setProduct(newlocation.getProduct());
    return location;
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
