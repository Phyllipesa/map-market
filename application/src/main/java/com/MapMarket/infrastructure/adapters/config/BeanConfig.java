package com.MapMarket.infrastructure.adapters.config;

import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.logic.ValidationProduct;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.service.ProdutoService;
import com.MapMarket.infrastructure.adapters.output.persistence.ProdutoPersistenceAdapter;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import com.MapMarket.infrastructure.adapters.output.persistence.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

@Configuration
public class BeanConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProdutoEntity.class, Produto.class)
        .addMappings(mapper -> {
          mapper.map(ProdutoEntity::getId, Produto::setKey);
        });
    modelMapper.typeMap(Produto.class, ProdutoResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Produto::getKey, ProdutoResponseDto::setId);
        });
    return modelMapper;
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
  public ProdutoService produtoService(
      ProdutoPersistenceAdapter produtoPersistenceAdapter,
      ValidationProduct validationProduct,
      PagedResourcesAssembler<ProdutoResponseDto> pagedResourcesAssembler,
      EntityMapper entityMapper,
      FindAllOutput<Produto> findAllOutput
  ) {
    return new ProdutoService(produtoPersistenceAdapter, findAllOutput, validationProduct, pagedResourcesAssembler, entityMapper);
  }
}
