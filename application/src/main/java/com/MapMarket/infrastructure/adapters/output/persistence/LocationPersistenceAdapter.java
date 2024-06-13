package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.logging.Logger;

public class LocationPersistenceAdapter implements OutputPort<Location>, FindAllOutput<Location> {

    private final Logger logger = Logger.getLogger(LocationPersistenceAdapter.class.getName());
    private final LocationRepository locationRepository;
    private final EntityMapper entityMapper;

  public LocationPersistenceAdapter(LocationRepository locationRepository, EntityMapper entityMapper) {
        this.locationRepository = locationRepository;
        this.entityMapper = entityMapper;
    }

  @Override
  public Page<Location> findAll(Pageable pageable) {
    logger.info("Finding all registers!");

    Page<LocationEntity> page = locationRepository.findAllLocationsWithProducts(pageable);
    return page.map(p -> entityMapper.parseObject(p, Location.class));
  }

  @Override
  public Optional<Location> findById(Long id) {
    logger.info("locating a product by Id!");
    return Optional.ofNullable(entityMapper.parseObject(locationRepository.findById(id), Location.class));
  }

  @Override
  public Optional<Location> create(Location location) {
    return Optional.empty();
  }

  @Override
  public Location update(Long id, Location location) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
