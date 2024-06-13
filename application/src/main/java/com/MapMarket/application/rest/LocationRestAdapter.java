package com.MapMarket.application.rest;

import com.MapMarket.application.rest.requestDto.LocationRequestDto;
import com.MapMarket.application.rest.responseDto.LocationResponseDto;
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
@RequestMapping("api/v1/location")
public class LocationRestAdapter {

  private final UseCase<LocationRequestDto, LocationResponseDto> useCase;
  private final FindAllUseCase<LocationResponseDto> findAllUseCase;

  public LocationRestAdapter(UseCase<LocationRequestDto, LocationResponseDto> useCase, FindAllUseCase<LocationResponseDto> findAllUseCase) {
    this.findAllUseCase = findAllUseCase;
    this.useCase = useCase;
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
}
