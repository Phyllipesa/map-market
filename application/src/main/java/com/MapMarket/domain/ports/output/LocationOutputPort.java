package com.MapMarket.domain.ports.output;

import java.util.Optional;

public interface LocationOutputPort<T> {
  Optional<T> findById(Long id);
  Optional<T> findLocationByProductId(Long id);
  T subscribingProduct(T t);
  T unsubscribingProduct(Long id);
  boolean existLocationWithProduct(Long id);
  boolean existResource(Long id);
}
