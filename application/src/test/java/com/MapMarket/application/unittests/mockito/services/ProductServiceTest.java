package com.MapMarket.application.unittests.mockito.services;

import com.MapMarket.application.rest.requestDto.ProdutoRequestDto;
import com.MapMarket.application.rest.responseDto.ProdutoResponseDto;
import com.MapMarket.application.unittests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unittests.mocks.MockProduct;
import com.MapMarket.domain.logic.ValidationProduct;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.service.ProdutoService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

  MockProduct input;
  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    input = new MockProduct();
    MockitoAnnotations.openMocks(this);
  }

  @Test
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
  void create() {
    //GIVEN
    var handler = new ProdutoService(new FakeOutputPort(), null, new ValidationProduct(), null, entityMapper);
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(2);

    //WHEN
    var result = handler.create(produtoRequestDto);

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
  void update() {
    //GIVEN
    var handler = new ProdutoService(new FakeOutputPort(), null, new ValidationProduct(), null, entityMapper);
    ProdutoRequestDto produtoRequestDto = input.mockRequestDto(3);

    //WHEN
    var result = handler.update(1L, produtoRequestDto);

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
  void delete() {
    //GIVEN
    FakeOutputPort  fakeOutputPort = mock(FakeOutputPort.class);
    var handler = new ProdutoService(fakeOutputPort, null, null, null, entityMapper);

    //WHEN
    when(fakeOutputPort.findById(1L)).thenReturn(Optional.of(mock(Produto.class)));
    handler.delete(1L);

    //THEN
    verify(fakeOutputPort, times(1)).findById(1L);
    verify(fakeOutputPort, times(1)).delete(1L);
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
