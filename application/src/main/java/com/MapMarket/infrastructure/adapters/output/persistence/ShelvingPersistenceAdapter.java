package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ShelvingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.logging.Logger;

public class ShelvingPersistenceAdapter implements OutputPort<ShelvingUnit>, FindAllOutput<ShelvingUnit> {

  private final Logger logger = Logger.getLogger(ShelvingPersistenceAdapter.class.getName());
  private final ShelvingRepository shelvingRepository;
  private final EntityMapper entityMapper;

  public ShelvingPersistenceAdapter(ShelvingRepository shelvingRepository, EntityMapper entityMapper) {
    this.shelvingRepository = shelvingRepository;
    this.entityMapper = entityMapper;
  }


  @Override
  public Page<ShelvingUnit> findAll(Pageable pageable) {
    logger.info("Finding all shelving units!");
    return null;
  }

  @Override
  public Optional<ShelvingUnit> findById(Long id) {
    logger.info("Finding a shelving unit by Id!");
    return Optional.empty();
  }

  @Override
  public Optional<ShelvingUnit> create(ShelvingUnit shelvingUnit) {
    logger.info("Creating a shelving unit!");
    return Optional.empty();
  }

  @Override
  public ShelvingUnit update(Long id, ShelvingUnit shelvingUnit) {
    logger.info("Updating a shelving unit!");
    return null;
  }

  @Override
  public void delete(Long id) {
    logger.info("Deleting a shelving unit!");
  }
}
