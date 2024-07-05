package com.MapMarket.application.unitTests.fakeClasses;

import com.MapMarket.application.unitTests.mocks.MockProduct;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.ports.output.OutputPort;

import java.util.Objects;
import java.util.Optional;

public class FakeOutputPort implements OutputPort<Product> {

  MockProduct mockProduct = new MockProduct();

  @Override
  public Optional<Product> findById(Long id) {
    if (id == 3) {
      Product product = mockProduct.mockModel();
      product.setId(id);
      return Optional.of(product);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Product> create(Product product) {
    if (product == null || Objects.equals(product.getName(), "Exception")) {
      return Optional.empty();
    }
    product.setId(2L);
    return Optional.of(product);
  }

  @Override
  public Product update(Long id, Product product) {
    product.setId(id);
    product.setPrice(11.20);
    return product;
  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public boolean existResource(Long id) {
    return id == 1;
  }
}
