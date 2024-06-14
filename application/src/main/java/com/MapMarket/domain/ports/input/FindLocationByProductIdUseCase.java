package com.MapMarket.domain.ports.input;

public interface FindLocationByProductIdUseCase<OutPut> {
  OutPut findLocationByProductId(Long id);
}
