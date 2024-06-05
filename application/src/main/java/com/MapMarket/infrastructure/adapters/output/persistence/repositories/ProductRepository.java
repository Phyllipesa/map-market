package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
