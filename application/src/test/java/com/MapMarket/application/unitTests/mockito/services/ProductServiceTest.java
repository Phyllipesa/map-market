package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unitTests.mocks.MockProduct;
import com.MapMarket.domain.exception.*;
import com.MapMarket.domain.exception.constants.Constant;
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
public class ProductServiceTest {

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
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(2);

    //WHEN
    var result = service.create(produtoRequestDto);

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
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(3);

    //WHEN
    var result = service.update(1L, produtoRequestDto);

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

  @Test
  @Order(4)
  void findById_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, null, null, entityMapper);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
      service.findById(2L);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(5)
  void create_REQUIRED_OBJECT_IS_NULL_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.NULL_NOT_ALLOWED;

    //WHEN
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      service.create(null);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(6)
  void create_PARAMETER_name_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.REQUIRED_PARAMETER + "nome" + Constant.IS_NULL_OR_BLANK;
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(0);
    produtoRequestDto.setNome(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> {
      service.create(produtoRequestDto);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(7)
  void create_NEGATIVE_PRICE_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.NEGATIVE_NOT_ALLOWED;
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(0);
    produtoRequestDto.setPreco(-10.0);

    //WHEN
    Exception exception = assertThrows(NegativePriceException.class, () -> {
      service.create(produtoRequestDto);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(8)
  void create_PARAMETER_preco_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.REQUIRED_PARAMETER + "preco" + Constant.IS_NULL_OR_BLANK;
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(0);
    produtoRequestDto.setPreco(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> {
      service.create(produtoRequestDto);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(9)
  void create_PRODUCT_CREATION_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    String expectedMessage = Constant.ERROR_CREATING_PRODUCT;
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(0);
    produtoRequestDto.setNome("Exception");

    //WHEN
    Exception exception = assertThrows(ProductCreationException.class, () -> {
      service.create(produtoRequestDto);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(10)
  void update_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(3);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
      service.update(2L,produtoRequestDto);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(11)
  void delete_PRODUCT_NOT_FOUND_EXCEPTION() {
    //GIVEN
    var service = new ProdutoService(new FakeOutputPort(), null, new ProductValidator(), null, entityMapper);
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(3);
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
      service.delete(2L);
    });

    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }



  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProdutoEntity.class, Produto.class)
        .addMappings(mapper -> {
          mapper.map(ProdutoEntity::getId, Produto::setId);
        });
    modelMapper.typeMap(Produto.class, ProdutoResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Produto::getId, ProdutoResponseDto::setKey);
        });
    return modelMapper;
  }


  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }
}
