package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unitTests.mocks.MockProduct;
import com.MapMarket.domain.exception.NegativePriceException;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.ProductCreationException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.service.ProductService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceExceptionTest {

  MockProduct input;
  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    input = new MockProduct();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Order(0)
  void findById_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, null, null, entityMapper);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(2L));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(1)
  void create_PARAMETER_name_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.requiredParameterMessage("name");
    ProductRequestDto productRequestDto = input.mockRequestDto(0);
    productRequestDto.setName(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> service.create(productRequestDto));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(2)
  void create_NEGATIVE_price_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.NEGATIVE_NOT_ALLOWED;
    ProductRequestDto productRequestDto = input.mockRequestDto(0);
    productRequestDto.setPrice(-10.0);

    //WHEN
    Exception exception = assertThrows(NegativePriceException.class, () -> service.create(productRequestDto));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(3)
  void create_PARAMETER_price_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.requiredParameterMessage("price");
    ProductRequestDto productRequestDto = input.mockRequestDto(0);
    productRequestDto.setPrice(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> service.create(productRequestDto));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(4)
  void create_PRODUCT_CREATION_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.ERROR_CREATING_PRODUCT;
    ProductRequestDto productRequestDto = input.mockRequestDto(0);
    productRequestDto.setName("Exception");

    //WHEN
    Exception exception = assertThrows(ProductCreationException.class, () -> service.create(productRequestDto));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(5)
  void update_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProductRequestDto productRequestDto = input.mockRequestDto(3);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.update(2L, productRequestDto));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(6)
  void delete_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(2L));

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProductEntity.class, Product.class)
        .addMappings(mapper -> mapper.map(ProductEntity::getId, Product::setId));
    modelMapper.typeMap(Product.class, ProductResponseDto.class)
        .addMappings(mapper -> mapper.map(Product::getId, ProductResponseDto::setKey));
    return modelMapper;
  }

  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }
}
