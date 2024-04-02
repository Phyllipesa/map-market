package com.MapMarket.domain.ports.input;

import com.MapMarket.domain.exception.ProdutoNotFoundException;

import java.util.List;

public interface UseCase<T> {

  List<T> findAll() throws ProdutoNotFoundException;
}
