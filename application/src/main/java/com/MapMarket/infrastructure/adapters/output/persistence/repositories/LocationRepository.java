package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
  @Query("SELECT l FROM LocationEntity l JOIN FETCH l.product")
  List<LocationEntity> findAllLocationsWithProducts();
}
