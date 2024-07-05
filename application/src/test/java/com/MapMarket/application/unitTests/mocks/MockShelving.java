package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.domain.models.ShelvingUnit;

public class MockShelving {

  public ShelvingUnit mockModel() {
    return mockModel(1);
  }

  public ShelvingUnit mockModel(Integer number) {
    ShelvingUnit shelvingUnit = new ShelvingUnit();
    shelvingUnit.setId(number.longValue());
    shelvingUnit.setUnit(number.longValue());
    shelvingUnit.setSideA("Test side A");
    shelvingUnit.setSideB("Test side B");
    return shelvingUnit;
  }

  public ShelvingRequestDto mockRequestDto() {
    ShelvingRequestDto shelvingRequestDto = new ShelvingRequestDto();
    shelvingRequestDto.setUnit(2L);
    shelvingRequestDto.setSideA("Test side A");
    shelvingRequestDto.setSideB(null);
    return shelvingRequestDto;
  }
}
