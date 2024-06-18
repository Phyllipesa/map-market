package com.MapMarket.domain.ports.input;

public interface LocationUseCase<Input, OutPut> {
  OutPut findById(Long id);
  OutPut findLocationByProductId(Long id);
  OutPut create(Input input);
  OutPut update(Long locationId, Long productId);
  OutPut unsubscribingProduct(Long locationId);
  void delete(Long id);
}
