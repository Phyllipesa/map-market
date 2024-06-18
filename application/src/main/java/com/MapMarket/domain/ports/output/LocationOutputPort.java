package com.MapMarket.domain.ports.output;

import java.util.Optional;

public interface LocationOutputPort<T> {
  Optional<T> findById(Long id);
  Optional<T> findLocationByProductId(Long id);
  Optional<T> create(T t);
  T update(T t);
  T unsubscribingProduct(Long id);
  void delete(Long id);
}
