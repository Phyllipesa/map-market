package com.MapMarket.application.unitTests.fakeClasses;

import com.MapMarket.application.unitTests.mocks.MockShelving;
import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.ports.output.OutputPort;

import java.util.Objects;
import java.util.Optional;

public class FakeShelvingOutputPort implements OutputPort<ShelvingUnit> {

  public MockShelving mockShelving = new MockShelving();

  @Override
  public Optional<ShelvingUnit> findById(Long id) {
    if (id == 1) {
      ShelvingUnit shelvingUnit = mockShelving.mockModel();
      return Optional.of(shelvingUnit);
    }
    return Optional.empty();
  }

  @Override
  public Optional<ShelvingUnit> create(ShelvingUnit shelvingUnit) {
    if (shelvingUnit == null || Objects.equals(shelvingUnit.getSideA(), "Exception")) {
      return Optional.empty();
    }
    shelvingUnit.setId(2L);
    return Optional.of(shelvingUnit);
  }

  @Override
  public ShelvingUnit update(Long id, ShelvingUnit shelvingUnit) {
    existResource(id);
    shelvingUnit.setId(id);
    shelvingUnit.setSideB("Test side B");
    return shelvingUnit;
  }

  @Override
  public void delete(Long id) {
  }

  public boolean existResource(Long id) {
    return id == 1;
  }
}
