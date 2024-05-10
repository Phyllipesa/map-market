package com.MapMarket.domain.ports.input;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface FindAllUseCase<T> {
    PagedModel<EntityModel<T>> findAll(Pageable pageable);
}
