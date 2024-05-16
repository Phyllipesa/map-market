package com.MapMarket.infrastructure.adapters.config;

import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.logic.ProductValidator;
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

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProdutoEntity.class, Produto.class)
        .addMappings(mapper -> {
          mapper.map(ProdutoEntity::getId, Produto::setId);
        });
    modelMapper.typeMap(Produto.class, ProdutoResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Produto::getId, ProdutoResponseDto::setKey);
        });
    return modelMapper;
  }

  @Bean
  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }

  @Bean
  public ProductValidator validationProduct() {
    return new ProductValidator();
  }

  @Bean
  public ProdutoPersistenceAdapter produtoPersistenceAdapter(ProdutoRepository produtoRepository, EntityMapper entityMapper) {
    return new ProdutoPersistenceAdapter(produtoRepository, entityMapper);
  }

  @Bean
  public ProdutoService produtoService(
      ProdutoPersistenceAdapter produtoPersistenceAdapter,
      ProductValidator productValidator,
      PagedResourcesAssembler<ProdutoResponseDto> pagedResourcesAssembler,
      EntityMapper entityMapper,
      FindAllOutput<Produto> findAllOutput
  ) {
    return new ProdutoService(produtoPersistenceAdapter, findAllOutput, productValidator, pagedResourcesAssembler, entityMapper);
  }
}
