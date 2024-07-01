package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;

public class MockLocation {

  public Location mockModel() {
    return mockModel(1L);
  }

  public LocationResponseDto mockResponseDto() {
    return mockResponseDto(1L);
  }

  public Location mockModel(Long number) {
    Location model = new Location();
    Product product = mockProductModel(number);

    model.setId(number);
    model.setShelvingUnitId(number);
    model.setSide("Side A Test");
    model.setPart(1L);
    model.setShelf(1L);
    model.setProduct(product);
    return model;
  }

  public LocationResponseDto mockResponseDto(Long number) {
    LocationResponseDto dto = new LocationResponseDto();
    dto.setKey(number);
    dto.setShelvingUnit(number);
    dto.setPart(number);
    dto.setShelf(number);
    dto.setSide("Side A Test");
    dto.setProduct(mockProductResponseDto(number));
    return dto;
  }

  public Product mockProductModel(Long number) {
    Product model = new Product();
    model.setId(number);
    model.setName("Product Name Test" + number);
    model.setPrice(14.50);
    return model;
  }

  public ProductResponseDto mockProductResponseDto(Long number) {
    ProductResponseDto dto = new ProductResponseDto();
    dto.setKey(number);
    dto.setName("Product Name Test" + number);
    dto.setPrice(14.50);
    return dto;
  }
}
