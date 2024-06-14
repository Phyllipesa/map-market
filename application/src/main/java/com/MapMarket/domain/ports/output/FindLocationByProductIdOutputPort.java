package com.MapMarket.domain.ports.output;

import java.util.Optional;

public interface FindLocationByProductIdOutputPort<T> {
  Optional<T> findLocationByProductId(Long productId);
}
