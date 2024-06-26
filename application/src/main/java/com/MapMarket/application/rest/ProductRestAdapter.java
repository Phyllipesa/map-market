package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.UseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductRestAdapter {

  private final UseCase<ProductRequestDto, ProductResponseDto> useCase;
  private final FindAllUseCase<ProductResponseDto> findAllUseCase;

  public ProductRestAdapter(
      UseCase<ProductRequestDto, ProductResponseDto> useCase,
      FindAllUseCase<ProductResponseDto> findAllUseCase
  ) {
    this.findAllUseCase = findAllUseCase;
    this.useCase = useCase;
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<ProductResponseDto>>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
    return ResponseEntity.ok(findAllUseCase.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(useCase.findById(id));
  }

  @PostMapping
  public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productDto) {
    return ResponseEntity.status(201).body(useCase.create(productDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody ProductRequestDto productDto) {
    return ResponseEntity.ok(useCase.update(id, productDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductResponseDto> delete(@PathVariable Long id) {
    useCase.delete(id);
    return ResponseEntity.noContent().build();
  }
}
