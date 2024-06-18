package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelvingRepository extends JpaRepository<ShelvingUnitEntity, Long> {
}