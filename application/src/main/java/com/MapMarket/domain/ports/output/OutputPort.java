package com.MapMarket.domain.ports.output;

import java.util.Optional;

public interface OutputPort<T> {

  Optional<T> findById(Long id);
  Optional<T> create(T t);
  Optional<T> update(Long id, T t);
  void delete(Long id);
}
