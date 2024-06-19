package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
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
    Page<ShelvingUnitEntity> page = shelvingRepository.findAll(pageable);
    return page.map(p -> entityMapper.parseObject(p, ShelvingUnit.class));
  }

  @Override
  public Optional<ShelvingUnit> findById(Long id) {
    logger.info("Finding a shelving unit by Id!");
    return Optional.ofNullable(entityMapper.parseObject(shelvingRepository.findById(id), ShelvingUnit.class));
  }

  @Override
  public Optional<ShelvingUnit> create(ShelvingUnit shelvingUnit) {
    logger.info("Creating a shelving unit!");
    ShelvingUnitEntity entity = entityMapper.parseObject(shelvingUnit, ShelvingUnitEntity.class);
    return Optional.ofNullable(entityMapper.parseObject(shelvingRepository.save(entity), ShelvingUnit.class));
  }

  @Override
  public ShelvingUnit update(Long id, ShelvingUnit shelvingUnit) {
    logger.info("Updating a shelving unit!");
    ShelvingUnitEntity entity = shelvingRepository.getReferenceById(id);
    updateData(entity, shelvingUnit);
    return entityMapper.parseObject(shelvingRepository.save(entity), ShelvingUnit.class);
  }

  @Override
  public void delete(Long id) {
    logger.info("Deleting a shelving unit!");
  }

  private void updateData(ShelvingUnitEntity entity, ShelvingUnit shelvingUnit) {
    entity.setUnit(shelvingUnit.getUnit());
    entity.setSideA(shelvingUnit.getSideA());
    entity.setSideB(shelvingUnit.getSideB());
  }
}
