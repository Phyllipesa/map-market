package com.MapMarket.domain.service;

import com.MapMarket.application.rest.ProductRestAdapter;
import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.domain.exception.ProductCreationException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.models.Product;
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

public class ProductService implements UseCase<ProductRequestDto, ProductResponseDto>, FindAllUseCase<ProductResponseDto> {

  private final OutputPort<Product> outputPort;
  private final FindAllOutput<Product> findAllOutput;
  private final ProductValidator productValidator;
  private final PagedResourcesAssembler<ProductResponseDto> assembler;
  private final EntityMapper entityMapper;

  public ProductService(
      OutputPort<Product> outputPort,
      FindAllOutput<Product> findAllOutput,
      ProductValidator productValidator,
      PagedResourcesAssembler<ProductResponseDto> assembler,
      EntityMapper entityMapper
  ) {
    this.findAllOutput = findAllOutput;
    this.assembler = assembler;
    this.outputPort = outputPort;
    this.productValidator = productValidator;
    this.entityMapper = entityMapper;
  }

  @Override
  public PagedModel<EntityModel<ProductResponseDto>> findAll(Pageable pageable) {
    Page<Product> allProducts = findAllOutput.findAll(pageable);
    if (allProducts.isEmpty()) throw new ResourceNotFoundException(Constant.PRODUCTS_NOT_FOUND);

    Page<ProductResponseDto> allProductsDto = allProducts.map(
        p -> entityMapper.parseObject(p, ProductResponseDto.class));

    allProductsDto.map(
        p -> p.add(
            linkTo(methodOn(ProductRestAdapter.class)
                .findById(p.getKey())).withSelfRel()));

    Link link = linkTo(
        methodOn(ProductRestAdapter.class)
            .findAll(
                pageable.getPageNumber(),
                pageable.getPageSize()
            )
    ).withSelfRel();
    return assembler.toModel(allProductsDto, link);
  }

  @Override
  public ProductResponseDto findById(Long id) {
    Product product = outputPort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(Constant.PRODUCT_NOT_FOUND + id));

    ProductResponseDto productDto = entityMapper.parseObject(product, ProductResponseDto.class);
    productDto.add(linkTo(methodOn(ProductRestAdapter.class).findById(id)).withSelfRel());
    return productDto;
  }

  @Override
  public ProductResponseDto create(ProductRequestDto productRequestDto) {
    productValidator.validate(productRequestDto);
    Product product = outputPort.create(entityMapper.parseObject(productRequestDto, Product.class))
        .orElseThrow(() -> new ProductCreationException(Constant.ERROR_CREATING_PRODUCT));

    ProductResponseDto productDto = entityMapper.parseObject(product, ProductResponseDto.class);
    productDto.add(linkTo(methodOn(ProductRestAdapter.class).findById(productDto.getKey())).withSelfRel());
    return productDto;
  }

  @Override
  public ProductResponseDto update(Long id, ProductRequestDto productRequestDto) {
    productValidator.validate(productRequestDto);
    findById(id);
    Product product =  outputPort.update(id, entityMapper.parseObject(productRequestDto, Product.class));
    ProductResponseDto productDto = entityMapper.parseObject(product, ProductResponseDto.class);
    productDto.add(linkTo(methodOn(ProductRestAdapter.class).findById(productDto.getKey())).withSelfRel());
    return productDto;
  }

  @Override
  public void delete(Long id) {
    findById(id);
    outputPort.delete(id);
  }
}
