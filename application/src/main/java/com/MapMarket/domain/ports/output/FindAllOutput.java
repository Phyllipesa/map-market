package com.MapMarket.domain.ports.output;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindAllOutput<T> {
  Page<T> findAll(Pageable pageable);
}
