package com.MapMarket.application.rest;

import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/produto")
public class ProdutoController {

  private final UseCase<Produto> useCase;
  private final EntityMapper entityMapper;

  public ProdutoController(UseCase<Produto> useCase, EntityMapper entityMapper) {
    this.useCase = useCase;
    this.entityMapper = entityMapper;
  }

  @GetMapping
  public ResponseEntity<List<ProdutoResponseDto>> findAll() {
    List<Produto> produtoList = useCase.findAll();
    return ResponseEntity.ok(entityMapper.parseListObject(produtoList, ProdutoResponseDto.class));
  }
}
