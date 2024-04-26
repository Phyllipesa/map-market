package com.MapMarket.domain.ports.input;

import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.ResourceNotFoundException;

public interface UseCase<T> {

//  List<T> findAll(Pageable pageable) throws ResourceNotFoundException;
  T findById(Long id);
  T create(T t);
  T update(Long id, T t);
}
