package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.LocationOutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.logging.Logger;

public class LocationPersistenceAdapter implements LocationOutputPort<Location>, FindAllOutput<Location> {

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
    logger.info("Finding a register by Id!");
    return Optional.ofNullable(entityMapper.parseObject(locationRepository.findById(id), Location.class));
  }

  public Optional<Location> findLocationByProductId(Long id) {
    logger.info("Finding a register by product id!");
    return Optional.ofNullable(entityMapper.parseObject(locationRepository.findLocationByProductId(id), Location.class));
  }

  @Override
  public Optional<Location> create(Location location) {
    logger.info("Register a product!");
    LocationEntity  locationEntity = entityMapper.parseObject(location, LocationEntity.class);
    return Optional.ofNullable(entityMapper.parseObject(locationRepository.save(locationEntity), Location.class));
  }

  @Override
  public Location update(Location location) {
    logger.info("Updating a product location!");
    LocationEntity  locationEntity = entityMapper.parseObject(location, LocationEntity.class);
    return entityMapper.parseObject(locationRepository.save(locationEntity), Location.class);
  }

  @Override
  public Location unsubscribingProduct(Long id) {
    logger.info("Unsubscribing a product in location!");
    LocationEntity locationEntity = locationRepository.findById(id).get();
    locationEntity.setProduct(null);
    return entityMapper.parseObject(locationRepository.save(locationEntity), Location.class);
  }


  @Override
  public void delete(Long locationId) {
    logger.info("Deleting a location!");
    locationRepository.delete(locationRepository.findById(locationId).get());
  }
}
