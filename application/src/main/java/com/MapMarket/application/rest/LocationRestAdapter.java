package com.MapMarket.application.rest;

import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.LocationUseCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/location")
public class LocationRestAdapter {

  private final LocationUseCase<LocationResponseDto> useCase;
  private final FindAllUseCase<LocationResponseDto> findAllUseCase;

  public LocationRestAdapter(
      LocationUseCase<LocationResponseDto> useCase,
      FindAllUseCase<LocationResponseDto> findAllUseCase
  ) {
    this.useCase = useCase;
    this.findAllUseCase = findAllUseCase;
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<LocationResponseDto>>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
    return ResponseEntity.ok(findAllUseCase.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LocationResponseDto> findById(@PathVariable(value = "id")Long id) {
    return ResponseEntity.ok(useCase.findById(id));
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<LocationResponseDto> findLocationByProductId(@PathVariable(value = "id")Long id) {
    return ResponseEntity.ok(useCase.findLocationByProductId(id));
  }

  @PutMapping("/{locationId}/{productId}")
  public ResponseEntity<LocationResponseDto> subscribingProduct(@PathVariable Long locationId, @PathVariable Long productId) {
    return ResponseEntity.ok(useCase.subscribingProduct(locationId, productId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LocationResponseDto> unsubscribingProduct(@PathVariable Long id) {
    return ResponseEntity.ok(useCase.unsubscribingProduct(id));
  }
}
