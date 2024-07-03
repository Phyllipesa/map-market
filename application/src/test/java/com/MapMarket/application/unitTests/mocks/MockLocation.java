package com.MapMarket.application.unitTests.mocks;

import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;

public class MockLocation {

  public Location mockModel() {
    return mockModelWithProduct(1L);
  }

  public Location mockModelWithProduct(Long number) {
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

  public Location mockModelWithoutProduct(Long number) {
    Location model = new Location();

    model.setId(number);
    model.setShelvingUnitId(number);
    model.setSide("Side A Test");
    model.setPart(number);
    model.setShelf(number);
    return model;
  }

  public Product mockProductModel(Long number) {
    Product model = new Product();
    model.setId(2L);
    model.setName("Product Name Test " + number);
    model.setPrice(14.50);
    return model;
  }
}
