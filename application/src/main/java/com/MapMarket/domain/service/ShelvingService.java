package com.MapMarket.domain.service;

import com.MapMarket.application.rest.ShelvingRestAdapter;
import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.ports.input.FindAllUseCase;
import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.domain.ports.output.FindAllOutput;
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

public class ShelvingService implements UseCase<ShelvingRequestDto, ShelvingResponseDto>, FindAllUseCase<ShelvingResponseDto> {

  private final OutputPort<ShelvingUnit> outputPort;
  private final FindAllOutput<ShelvingUnit> findAllOutput;
  private final PagedResourcesAssembler<ShelvingResponseDto> assembler;
  private final EntityMapper entityMapper;

  public ShelvingService(
      OutputPort<ShelvingUnit> outputPort,
      FindAllOutput<ShelvingUnit> findAllOutput,
      PagedResourcesAssembler<ShelvingResponseDto> assembler,
      EntityMapper entityMapper
  ) {
    this.outputPort = outputPort;
    this.findAllOutput = findAllOutput;
    this.assembler = assembler;
    this.entityMapper = entityMapper;
  }

  @Override
  public PagedModel<EntityModel<ShelvingResponseDto>> findAll(Pageable pageable) {
    Page<ShelvingUnit> allShelvingUnits = findAllOutput.findAll(pageable);
    if (allShelvingUnits.isEmpty()) throw new ResourceNotFoundException("Shelving units not found!");

    Page<ShelvingResponseDto> allShelvingDto = allShelvingUnits.map(
        p -> entityMapper.parseObject(p, ShelvingResponseDto.class));

    allShelvingDto.map(
        p -> p.add(
            linkTo(methodOn(ShelvingRestAdapter.class)
                .findById(p.getKey())).withSelfRel()));

    Link link = linkTo(
        methodOn(ShelvingRestAdapter.class)
            .findAll(
                pageable.getPageNumber(),
                pageable.getPageSize()
            )
    ).withSelfRel();
    return assembler.toModel(allShelvingDto, link);
  }

  @Override
  public ShelvingResponseDto findById(Long id) {
    return null;
  }

  @Override
  public ShelvingResponseDto create(ShelvingRequestDto shelvingRequestDto) {
    return null;
  }

  @Override
  public ShelvingResponseDto update(Long id, ShelvingRequestDto shelvingRequestDto) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
