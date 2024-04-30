package com.MapMarket.domain.service;

import com.MapMarket.application.rest.ProdutoRestAdapter;
import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.domain.exception.ProductCreationException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.ProdutoConstant;
import com.MapMarket.domain.logic.ValidationProduct;
import com.MapMarket.domain.models.Produto;
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

public class ProdutoService implements UseCase<ProdutoRequestDto, ProdutoResponseDto>, FindAllUseCase<ProdutoResponseDto> {

  private final OutputPort<Produto> outputPort;
  private final FindAllOutput<Produto> findAllOutput;
  private final ValidationProduct validationProduct;
  private final PagedResourcesAssembler<ProdutoResponseDto> assembler;
  private final EntityMapper entityMapper;

  public ProdutoService(OutputPort<Produto> outputPort, FindAllOutput<Produto> findAllOutput, ValidationProduct validationProduct, PagedResourcesAssembler<ProdutoResponseDto> assembler, EntityMapper entityMapper) {
    this.findAllOutput = findAllOutput;
    this.assembler = assembler;
    this.outputPort = outputPort;
    this.validationProduct = validationProduct;
    this.entityMapper = entityMapper;
  }

  @Override
  public PagedModel<EntityModel<ProdutoResponseDto>> findAll(Pageable pageable) {
    Page<Produto> allProducts = findAllOutput.findAll(pageable);
    if (allProducts.isEmpty()) {
      throw new ResourceNotFoundException("Não foram encontrados produtos");
    }

    Page<ProdutoResponseDto> allProductsDto = allProducts.map(
        p -> entityMapper.parseObject(p, ProdutoResponseDto.class));

    allProductsDto.map(
        p -> p.add(
            linkTo(methodOn(ProdutoRestAdapter.class)
                .findById(p.getId())).withSelfRel()));

    Link link = linkTo(
        methodOn(ProdutoRestAdapter.class)
            .findAll(
                pageable.getPageNumber(),
                pageable.getPageSize()
            )
    ).withSelfRel();
    return assembler.toModel(allProductsDto, link);
  }

  @Override
  public ProdutoResponseDto findById(Long id) {
    Produto product = outputPort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ProdutoConstant.PRODUCT_NOT_FOUND + id));

    ProdutoResponseDto productDto = entityMapper.parseObject(product, ProdutoResponseDto.class);
    productDto.add(linkTo(methodOn(ProdutoRestAdapter.class).findById(id)).withSelfRel());
    return productDto;
  }

  @Override
  public ProdutoResponseDto create(ProdutoRequestDto produtoRequestDto) {
    validationProduct.validate(produtoRequestDto);
    Produto product = outputPort.create(entityMapper.parseObject(produtoRequestDto, Produto.class))
        .orElseThrow(() -> new ProductCreationException(ProdutoConstant.ERROR_CREATING_PRODUCT));

    ProdutoResponseDto productDto = entityMapper.parseObject(product, ProdutoResponseDto.class);
    productDto.add(linkTo(methodOn(ProdutoRestAdapter.class).findById(productDto.getId())).withSelfRel());
    return productDto;
  }

  @Override
  public ProdutoResponseDto update(Long id, ProdutoRequestDto produtoRequestDto) {
    validationProduct.validate(produtoRequestDto);
    findById(id);
    Produto product =  outputPort.update(id, entityMapper.parseObject(produtoRequestDto, Produto.class))
        .orElseThrow(() -> new ResourceNotFoundException(ProdutoConstant.PRODUCT_NOT_FOUND + id));

    ProdutoResponseDto productDto = entityMapper.parseObject(product, ProdutoResponseDto.class);
    productDto.add(linkTo(methodOn(ProdutoRestAdapter.class).findById(productDto.getId())).withSelfRel());
    return productDto;
  }

  @Override
  public void delete(Long id) {
    findById(id);
    outputPort.delete(id);
  }
}
