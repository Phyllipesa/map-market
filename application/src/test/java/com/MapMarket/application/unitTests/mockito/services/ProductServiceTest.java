package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.requestDto.ProductRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unitTests.mocks.MockProduct;
import com.MapMarket.domain.logic.ProductValidator;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.service.ProdutoService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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
    var service = new ProdutoService(new FakeOutputPort(), null, null, null, entityMapper);

    //WHEN
    var result = service.findById(1L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getNome());
    assertNotNull(result.getPreco());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals("Product Name Test0", result.getNome());
    assertEquals(14.50, result.getPreco());
  }

  @Test
  @Order(1)
  void create() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProductRequestDto productRequestDto = input.mockRequestDto(2);

    //WHEN
    var result = service.create(productRequestDto);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getNome());
    assertNotNull(result.getPreco());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(2L, result.getKey());
    assertEquals("Product Name Test2", result.getNome());
    assertEquals(14.50, result.getPreco());
  }

  @Test
  @Order(2)
  void update() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProductRequestDto productRequestDto = input.mockRequestDto(3);

    //WHEN
    var result = service.update(1L, productRequestDto);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getNome());
    assertNotNull(result.getPreco());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals("Product Name Test3", result.getNome());
    assertEquals(11.20, result.getPreco());
  }

  @Test
  @Order(3)
  void delete() {
    //GIVEN
    FakeOutputPort  fakeOutputPort = mock(FakeOutputPort.class);
    var service = new ProdutoService(fakeOutputPort, null, null, null, entityMapper);

    //WHEN
    when(fakeOutputPort.findById(1L)).thenReturn(Optional.of(mock(Produto.class)));
    service.delete(1L);

    //THEN
    verify(fakeOutputPort, times(1)).findById(1L);
    verify(fakeOutputPort, times(1)).delete(1L);
  }

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProdutoEntity.class, Produto.class)
        .addMappings(mapper -> mapper.map(ProdutoEntity::getId, Produto::setId));
    modelMapper.typeMap(Produto.class, ProdutoResponseDto.class)
        .addMappings(mapper -> mapper.map(Produto::getId, ProdutoResponseDto::setKey));
    return modelMapper;
  }

  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }
}
