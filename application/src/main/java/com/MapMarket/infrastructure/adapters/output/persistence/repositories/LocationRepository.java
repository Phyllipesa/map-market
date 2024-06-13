package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
  @Query("SELECT l FROM LocationEntity l JOIN FETCH l.product")
  Page<LocationEntity> findAllLocationsWithProducts(Pageable pageable);

  @Query("SELECT l FROM LocationEntity l WHERE l.product_id =:id")
  LocationEntity findLocationByProductId(Long id);
}
