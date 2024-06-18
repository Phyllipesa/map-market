package com.MapMarket.domain.service;

import com.MapMarket.application.rest.LocationRestAdapter;
import com.MapMarket.application.rest.requestDto.LocationRequestDto;
import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.LocationUseCase;
import com.MapMarket.domain.ports.output.FindAllOutput;
import com.MapMarket.domain.ports.output.LocationOutputPort;
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

public class LocationService implements LocationUseCase<LocationRequestDto, LocationResponseDto>, FindAllUseCase<LocationResponseDto> {

  private final LocationOutputPort<Location> outputPort;
  private final OutputPort<Product> productOutputPort;
  private final FindAllOutput<Location> findAllOutput;
  private final PagedResourcesAssembler<LocationResponseDto> assembler;
  private final EntityMapper entityMapper;

  public LocationService(LocationOutputPort<Location> outputPort, OutputPort<Product> productOutputPort, FindAllOutput<Location> findAllOutput, PagedResourcesAssembler<LocationResponseDto> assembler, EntityMapper entityMapper) {
    this.outputPort = outputPort;
    this.productOutputPort = productOutputPort;
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

  @Override
  public LocationResponseDto findById(Long id) {
    Location location = outputPort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No register with id " + id));

    LocationResponseDto locationDto = entityMapper.parseObject(location, LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(LocationRestAdapter.class).findById(id)).withSelfRel());
    return locationDto;
  }

  public LocationResponseDto findLocationByProductId(Long id) {
    Location location = outputPort.findLocationByProductId(id)
        .orElseThrow(() -> new ResourceNotFoundException("No product registered with id " + id));

    LocationResponseDto locationDto = entityMapper.parseObject(location, LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(LocationRestAdapter.class).findLocationByProductId(id)).withSelfRel());
    return locationDto;
  }

  @Override
  public LocationResponseDto create(LocationRequestDto locationRequestDto) {
    Location location = entityMapper.parseObject(locationRequestDto, Location.class);
    Location  locationSaved = outputPort.create(location)
        .orElseThrow(() -> new ResourceNotFoundException("Error saving location"));

    LocationResponseDto locationDto = entityMapper.parseObject(locationSaved, LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(LocationRestAdapter.class).create(null)).withSelfRel());
    return entityMapper.parseObject(outputPort.create(locationSaved), LocationResponseDto.class);
  }

  @Override
  public LocationResponseDto update(Long locationId, Long productId) {
    Location location =  outputPort.findById(locationId)
        .orElseThrow(() -> new ResourceNotFoundException("No register with id " + locationId));

    Product product =  productOutputPort.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("No product found with id " + productId));

    location.setProduct(product);

    LocationResponseDto locationDto = entityMapper.parseObject(outputPort.update(location), LocationResponseDto.class);
    locationDto.add(linkTo(methodOn(LocationRestAdapter.class).update(locationId, productId)).withSelfRel());
    return locationDto;
  }

  @Override
  public void delete(Long locationId) {
    findById(locationId);
    outputPort.delete(locationId);
  }
}
