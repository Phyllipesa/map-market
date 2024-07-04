package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unitTests.mocks.MockProduct;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.service.ProductService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

  MockProduct input;
  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    input = new MockProduct();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Order(0)
  void findById() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, null, null, entityMapper);

    //WHEN
    var result = service.findById(3L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getName());
    assertNotNull(result.getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(3L, result.getKey());
    assertEquals("Product Name Test 3", result.getName());
    assertEquals(14.50, result.getPrice());
  }

  @Test
  @Order(1)
  void create() {
    //GIVEN
    var service = new ProductService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProductRequestDto productRequestDto = input.mockRequestDto(2);

    //WHEN
    var result = service.create(productRequestDto);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getName());
    assertNotNull(result.getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(2L, result.getKey());
    assertEquals("Product Name Test 2", result.getName());
    assertEquals(14.50, result.getPrice());
  }

  @Test
  @Order(2)
  void update() {
    //GIVEN
    var service = new ProductService(
        new FakeOutputPort(),
        null,
        new ProductValidator(),
        null,
        entityMapper
    );
    ProductRequestDto productRequestDto = input.mockRequestDto(3);

    //WHEN
    var result = service.update(1L, productRequestDto);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getName());
    assertNotNull(result.getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals("Product Name Test 3", result.getName());
    assertEquals(11.20, result.getPrice());
  }

  @Test
  @Order(3)
  void delete() {
    //GIVEN
    FakeOutputPort fakeOutputPort = mock(FakeOutputPort.class);
    var service = new ProductService(
        fakeOutputPort,
        null,
        null,
        null,
        null
    );

    //WHEN
    when(fakeOutputPort.existResource(1L)).thenReturn(true);
    service.delete(1L);

    //THEN
    verify(fakeOutputPort, times(1)).existResource(1L);
    verify(fakeOutputPort, times(1)).delete(1L);
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
