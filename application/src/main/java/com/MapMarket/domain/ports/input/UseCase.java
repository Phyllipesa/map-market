package com.MapMarket.domain.ports.input;

public interface UseCase<Input, OutPut> {

  OutPut findById(Long id);
  OutPut create(Input input);
  OutPut update(Long id, Input input);
  void delete(Long id);
}
