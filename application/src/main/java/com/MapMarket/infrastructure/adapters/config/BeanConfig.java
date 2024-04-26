package com.MapMarket.infrastructure.adapters.config;

import com.MapMarket.domain.logic.ValidationProduct;
import com.MapMarket.domain.service.ProdutoService;
import com.MapMarket.infrastructure.adapters.output.persistence.ProdutoPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public EntityMapper entityMapper() {
    return new EntityMapper();
  }

  @Bean
  public ValidationProduct validationProduct() {
    return new ValidationProduct();
  }

  @Bean
  public ProdutoPersistenceAdapter produtoPersistenceAdapter(ProdutoRepository produtoRepository, EntityMapper entityMapper) {
    return new ProdutoPersistenceAdapter(produtoRepository, entityMapper);
  }

  @Bean
  public ProdutoService produtoService(ProdutoPersistenceAdapter produtoPersistenceAdapter, ValidationProduct validationProduct) {
    return new ProdutoService(produtoPersistenceAdapter, validationProduct);
  }
}
