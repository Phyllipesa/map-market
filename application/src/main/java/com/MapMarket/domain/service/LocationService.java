package com.MapMarket.domain.service;

import com.MapMarket.application.rest.LocationRestAdapter;
import com.MapMarket.application.rest.ProductRestAdapter;
import com.MapMarket.application.rest.requestDto.LocationRequestDto;
import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.FindLocationByProductIdUseCase;
import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.FindLocationByProductIdOutputPort;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LocationService implements UseCase<LocationRequestDto, LocationResponseDto>, FindAllUseCase<LocationResponseDto>, FindLocationByProductIdUseCase<LocationResponseDto> {

  private final OutputPort<Location> outputPort;
  private final FindAllOutput<Location> findAllOutput;
  private final FindLocationByProductIdOutputPort<Location> findLocationByProductIdOutputPort;
  private final PagedResourcesAssembler<LocationResponseDto> assembler;
  private final EntityMapper entityMapper;

  public LocationService(OutputPort<Location> outputPort, FindAllOutput<Location> findAllOutput, FindLocationByProductIdOutputPort<Location> findLocationByProductIdOutputPort, PagedResourcesAssembler<LocationResponseDto> assembler, EntityMapper entityMapper) {
    this.outputPort = outputPort;
    this.findAllOutput = findAllOutput;
    this.findLocationByProductIdOutputPort = findLocationByProductIdOutputPort;
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

  @Override
  public LocationResponseDto findById(Long id) {
    Location location = outputPort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No register with id " + id));

    LocationResponseDto locationDto = entityMapper.parseObject(location, LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(ProductRestAdapter.class).findById(id)).withSelfRel());
    return locationDto;
  }

  public LocationResponseDto findLocationByProductId(Long id) {
    Location location = findLocationByProductIdOutputPort.findLocationByProductId(id)
        .orElseThrow(() -> new ResourceNotFoundException("No product registered with id " + id));

    LocationResponseDto locationDto = entityMapper.parseObject(location, LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(ProductRestAdapter.class).findById(id)).withSelfRel());
    return locationDto;
  }

  @Override
  public LocationResponseDto create(LocationRequestDto locationRequestDto) {
    return null;
  }

  @Override
  public LocationResponseDto update(Long id, LocationRequestDto locationRequestDto) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
