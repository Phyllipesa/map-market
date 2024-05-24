package com.MapMarket.domain.ports.output;

import java.util.Optional;

public interface FindByUserNameOutputPort<T> {
  Optional<T> findByUsername(String userName);
}
