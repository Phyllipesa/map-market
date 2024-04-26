package com.MapMarket.domain.ports.output;

import com.MapMarket.domain.models.Produto;

import java.util.Optional;

public interface OutputPort<T> {

//  Page<Produto> findAll(P p);

  Optional<Produto> findById(Long id);
  Optional<Produto> create(T t);
  Optional<Produto> update(Long id, T t);
}
