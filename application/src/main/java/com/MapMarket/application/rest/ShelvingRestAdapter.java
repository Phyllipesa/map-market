package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.UseCase;
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
