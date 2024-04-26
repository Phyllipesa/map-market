package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;

import java.util.Optional;
import java.util.logging.Logger;

public class ProdutoPersistenceAdapter implements OutputPort<Produto> {

  private Logger logger = Logger.getLogger(ProdutoPersistenceAdapter.class.getName());
  private final ProdutoRepository produtoRepository;
  private final EntityMapper entityMapper;

  public ProdutoPersistenceAdapter(ProdutoRepository produtoRepository, EntityMapper entityMapper) {
    this.produtoRepository = produtoRepository;
    this.entityMapper = entityMapper;
  }

//  @Override
//  public List<Produto> findAll() {
//    logger.info("Finding all products! (Adapter)");
//    return entityMapper.parseListObject(produtoRepository.findAll(), Produto.class);
//  }

  @Override
  public Optional<Produto> findById(Long id) {
    logger.info("Finding a product by Id!");
    return Optional.of(entityMapper.parseObject(produtoRepository.findById(id), Produto.class));
  }

  @Override
  public Optional<Produto> create(Produto produto) {
    logger.info("Creating a product!");
    ProdutoEntity entity = entityMapper.parseObject(produto, ProdutoEntity.class);
    return Optional.of(entityMapper.parseObject(produtoRepository.save(entity), Produto.class));
  }
}
