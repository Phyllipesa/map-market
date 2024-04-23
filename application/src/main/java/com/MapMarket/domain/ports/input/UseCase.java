package com.MapMarket.domain.ports.input;

import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.ProdutoNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UseCase<T> {

//  List<T> findAll(Pageable pageable) throws ProdutoNotFoundException;

  T findById(Long id) throws ProdutoNotFoundException;
  T create(T t) throws ParameterNotFoundException;
}
