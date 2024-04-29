package com.MapMarket.domain.ports.input;

public interface UseCase<T> {

//  List<T> findAll(Pageable pageable) throws ResourceNotFoundException;
  T findById(Long id);
  T create(T t);
  T update(Long id, T t);
   void delete(Long id);
}
