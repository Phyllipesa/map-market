package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;

import java.util.List;
import java.util.logging.Logger;

public class ProdutoPersistenceAdapter implements OutputPort<Produto> {

  private Logger logger = Logger.getLogger(ProdutoPersistenceAdapter.class.getName());
  private final ProdutoRepository produtoRepository;
  private final EntityMapper entityMapper;

  public ProdutoPersistenceAdapter(ProdutoRepository produtoRepository, EntityMapper entityMapper) {
    this.produtoRepository = produtoRepository;
    this.entityMapper = entityMapper;
  }

  @Override
  public List<Produto> findAll() {
    logger.info("Finding all products!");
    return entityMapper.parseListObject(produtoRepository.findAll(), Produto.class);
  }
}
