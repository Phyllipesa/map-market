package com.MapMarket.domain.ports.output;

public interface OutputPort<T> {

//  Page<Produto> findAll(P p);

  T findById(Long id);

  T create(T t);
}
