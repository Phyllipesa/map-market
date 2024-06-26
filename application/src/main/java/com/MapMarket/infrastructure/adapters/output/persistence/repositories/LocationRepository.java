package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
  @Query("SELECT l FROM LocationEntity l JOIN FETCH l.product")
  Page<LocationEntity> findAllLocationsWithProducts(Pageable pageable);

  @Query("SELECT l FROM LocationEntity l WHERE l.product.id =:id")
  Optional<LocationEntity> findLocationByProductId(@Param("id") Long id);

  @Query("SELECT COUNT(l) > 0 FROM LocationEntity l WHERE l.product.id = :productId")
  boolean existLocationWithProduct(@Param("productId") Long productId);
}
