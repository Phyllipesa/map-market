package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.domain.models.ShelvingUnit;

public class MockShelving {

  public ShelvingUnit mockModel() {
    return mockModel(1);
  }

  public ShelvingRequestDto mockRequestDto() {
    return  mockRequestDto(1);
  }

  public ShelvingResponseDto mockResponseDto() {
    return mockResponseDto(1);
  }

  public ShelvingUnit mockModel(Integer number) {
    ShelvingUnit shelvingUnit = new ShelvingUnit();
    shelvingUnit.setId(number.longValue());
    shelvingUnit.setUnit(number.longValue());
    shelvingUnit.setSideA("Test side A");
    shelvingUnit.setSideB("Test side B");
    return shelvingUnit;
  }

  public ShelvingRequestDto mockRequestDto(Integer number) {
    ShelvingRequestDto shelvingRequestDto = new ShelvingRequestDto();
    shelvingRequestDto.setUnit(number.longValue());
    shelvingRequestDto.setSideA("Test side A");
    shelvingRequestDto.setSideB("Test side B");
    return shelvingRequestDto;
  }

  public ShelvingResponseDto mockResponseDto(Integer number) {
    ShelvingResponseDto shelvingResponseDto = new ShelvingResponseDto();
    shelvingResponseDto.setKey(number.longValue());
    shelvingResponseDto.setUnit(number.longValue());
    shelvingResponseDto.setSideA("Test side A");
    shelvingResponseDto.setSideB("Test side B");
    return shelvingResponseDto;
  }
}
