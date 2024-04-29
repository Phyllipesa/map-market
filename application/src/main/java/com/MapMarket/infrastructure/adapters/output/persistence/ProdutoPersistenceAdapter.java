package com.MapMarket.infrastructure.adapters.output.persistence;

import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;

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
    return Optional.ofNullable(entityMapper.parseObject(produtoRepository.findById(id), Produto.class));
  }

  @Override
  public Optional<Produto> create(Produto produto) {
    logger.info("Creating a product!");
    ProdutoEntity entity = entityMapper.parseObject(produto, ProdutoEntity.class);
    return Optional.ofNullable(entityMapper.parseObject(produtoRepository.save(entity), Produto.class));
  }

  @Override
  public Optional<Produto> update(Long id, Produto produto) {
    logger.info("Updating a product!");
    ProdutoEntity entity = produtoRepository.getReferenceById(id);
    updateData(entity, produto);
    return Optional.ofNullable(entityMapper.parseObject(produtoRepository.save(entity), Produto.class));
  }

  @Override
  public void delete(Long id) {
    logger.info("Deleting a book!");
    produtoRepository.delete(produtoRepository.findById(id).get());
  }

  private void updateData(ProdutoEntity entity, Produto produto) {
    entity.setNome(produto.getNome());
    entity.setPreco(produto.getPreco());
  }
}
