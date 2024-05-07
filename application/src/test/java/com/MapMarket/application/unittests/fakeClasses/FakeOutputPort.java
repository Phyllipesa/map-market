package com.MapMarket.application.unittests.fakeClasses;

import com.MapMarket.application.unittests.mocks.MockProduct;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.output.OutputPort;

import java.util.Optional;

public class FakeOutputPort implements OutputPort<Produto> {

  MockProduct mockProduct = new MockProduct();

  @Override
  public Optional<Produto> findById(Long id) {
    if (id == 1) {
      Produto produto = mockProduct.mockModel();
      produto.setId(1L);
      return Optional.of(produto);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Produto> create(Produto produto) {
    if (produto != null) {
      produto.setId(2L);
      return Optional.of(produto);
    }
    return Optional.empty();
  }

  @Override
  public Produto update(Long id, Produto produto) {
    produto.setId(id);
    produto.setPreco(11.20);
    return produto;
  }

  @Override
  public void delete(Long id) {
    findById(id);
  }
}
