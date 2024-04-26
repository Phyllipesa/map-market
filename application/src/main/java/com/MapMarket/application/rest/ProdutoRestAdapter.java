package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/produto")
public class ProdutoRestAdapter {

  private final UseCase<Produto> useCase;
  private final EntityMapper entityMapper;

  public ProdutoRestAdapter(UseCase<Produto> useCase, EntityMapper entityMapper) {
    this.useCase = useCase;
    this.entityMapper = entityMapper;
  }

//  @GetMapping
//  public ResponseEntity<PagedModel<EntityModel<ProdutoResponseDto>>> findAll(
//      @RequestParam(value = "page", defaultValue = "0") Integer page,
//      @RequestParam(value = "size", defaultValue = "10") Integer size
//  ) {
//
//    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
//    List<Produto> produtoList = useCase.findAll(pageable);
//    return ResponseEntity.ok(entityMapper.parseListObject(produtoList, ProdutoResponseDto.class));
//  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdutoResponseDto> findById(@PathVariable(value = "id")Long id) {
    Produto produto = useCase.findById(id);
    return ResponseEntity.ok(entityMapper.parseObject(produto, ProdutoResponseDto.class));
  }

  @PostMapping
  public ResponseEntity<ProdutoResponseDto> create(@RequestBody ProdutoRequestDto produtoDto) throws ParameterNotFoundException {
    Produto novoProduto = entityMapper.parseObject(produtoDto, Produto.class);
    novoProduto = useCase.create(novoProduto);
    return ResponseEntity.ok(entityMapper.parseObject(novoProduto, ProdutoResponseDto.class));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProdutoResponseDto> update(@PathVariable(value = "id")Long id, @RequestBody ProdutoRequestDto produtoDto) {
    Produto produto = entityMapper.parseObject(produtoDto, Produto.class);
    produto = useCase.update(id, produto);
    return ResponseEntity.ok(entityMapper.parseObject(produto, ProdutoResponseDto.class));
  }
}
