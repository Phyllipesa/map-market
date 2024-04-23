package com.MapMarket.domain.ports.output;

import java.util.List;

public interface OutputPort<T> {

  List<T> findAll();

  T findById(Long id);
}
