package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.logging.Logger;

public class ProductPersistenceAdapter implements OutputPort<Product>, FindAllOutput<Product> {

  private final Logger logger = Logger.getLogger(ProductPersistenceAdapter.class.getName());
  private final ProductRepository productRepository;
  private final EntityMapper entityMapper;

  public ProductPersistenceAdapter(ProductRepository productRepository, EntityMapper entityMapper) {
    this.productRepository = productRepository;
    this.entityMapper = entityMapper;
  }

  @Override
  public Page<Product> findAll(Pageable  pageable) {
    logger.info("Finding all products!");

    Page<ProductEntity> page = productRepository.findAll(pageable);
    return page.map(p -> entityMapper.parseObject(p, Product.class));
  }

  @Override
  public Optional<Product> findById(Long id) {
    logger.info("Finding a product by Id!");
    return Optional.ofNullable(entityMapper.parseObject(productRepository.findById(id), Product.class));
  }

  @Override
  public Optional<Product> create(Product product) {
    logger.info("Creating a product!");
    ProductEntity entity = entityMapper.parseObject(product, ProductEntity.class);
    return Optional.ofNullable(entityMapper.parseObject(productRepository.save(entity), Product.class));
  }

  @Override
  public Product update(Long id, Product product) {
    logger.info("Updating a product!");
    ProductEntity entity = productRepository.getReferenceById(id);
    updateData(entity, product);
    return entityMapper.parseObject(productRepository.save(entity), Product.class);
  }

  @Override
  public void delete(Long id) {
    logger.info("Deleting a product!");
    productRepository.deleteById(id);
  }

  private void updateData(ProductEntity entity, Product product) {
    entity.setName(product.getName());
    entity.setPrice(product.getPrice());
  }

  public boolean existResource(Long id) {
    logger.info("Exist resource...");
    return !productRepository.existsById(id);
  }
}
