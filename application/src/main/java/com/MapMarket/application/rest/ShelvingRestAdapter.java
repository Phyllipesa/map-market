package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
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
@RequestMapping("api/v1/shelvingUnit")
public class ShelvingRestAdapter {

  private final UseCase<ShelvingRequestDto, ShelvingResponseDto> useCase;
  private final FindAllUseCase<ShelvingResponseDto> findAllUseCase;

  public ShelvingRestAdapter(UseCase<ShelvingRequestDto, ShelvingResponseDto> useCase, FindAllUseCase<ShelvingResponseDto> findAllUseCase) {
    this.useCase = useCase;
    this.findAllUseCase = findAllUseCase;
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<ShelvingResponseDto>>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "unit"));
    return ResponseEntity.ok(findAllUseCase.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShelvingResponseDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(useCase.findById(id));
  }

  @PostMapping
  public ResponseEntity<ShelvingResponseDto> create(@RequestBody ShelvingRequestDto shelvingDto) {
    return ResponseEntity.status(201).body(useCase.create(shelvingDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ShelvingResponseDto> update(@PathVariable Long id, @RequestBody ShelvingRequestDto shelvingDto) {
    return ResponseEntity.ok(useCase.update(id, shelvingDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    useCase.delete(id);
    return ResponseEntity.noContent().build();
  }
}
