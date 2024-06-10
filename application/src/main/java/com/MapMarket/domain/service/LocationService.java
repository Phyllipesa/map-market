package com.MapMarket.domain.service;

import com.MapMarket.application.rest.LocationRestAdapter;
import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LocationService implements FindAllUseCase<LocationResponseDto> {

  private final FindAllOutput<Location> findAllOutput;
  private final PagedResourcesAssembler<LocationResponseDto> assembler;
  private final EntityMapper entityMapper;

  public LocationService(FindAllOutput<Location> findAllOutput, PagedResourcesAssembler<LocationResponseDto> assembler, EntityMapper entityMapper) {
    this.findAllOutput = findAllOutput;
    this.assembler = assembler;
    this.entityMapper = entityMapper;
  }

  @Override
  public PagedModel<EntityModel<LocationResponseDto>> findAll(Pageable pageable) {
    Page<Location> allLocations = findAllOutput.findAll(pageable);
    if (allLocations.isEmpty()) throw new ResourceNotFoundException("No locations found");

    Page<LocationResponseDto> allLocationResponseDto = allLocations.map(
        p -> entityMapper.parseObject(p, LocationResponseDto.class));

    allLocationResponseDto.map(
        p -> p.add(
            linkTo(methodOn(LocationRestAdapter.class)
                .findById(p.getKey())).withSelfRel()));

    Link link = linkTo(
        methodOn(LocationRestAdapter.class)
            .findAll(
                pageable.getPageNumber(),
                pageable.getPageSize()
            )
    ).withSelfRel();
    return assembler.toModel(allLocationResponseDto, link);
  }
}
