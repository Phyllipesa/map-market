package com.MapMarket.domain.ports.input;

public interface LocationUseCase<OutPut> {
  OutPut findById(Long id);
  OutPut findLocationByProductId(Long id);
  OutPut subscribingProduct(Long locationId, Long productId);
  OutPut unsubscribingProduct(Long locationId);
}
