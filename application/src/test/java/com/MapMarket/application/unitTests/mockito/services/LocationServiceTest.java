package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeLocationOutputPort;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.service.LocationService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationServiceTest {

  private final EntityMapper entityMapper = entityMapper();

  @Test
  @Order(0)
  void findById() {
    //GIVEN
    var service = service();

    //WHEN
    var result = service.findById(1L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getShelvingUnit());
    assertNotNull(result.getSide());
    assertNotNull(result.getShelf());
    assertNotNull(result.getProduct());
    assertNotNull(result.getProduct().getKey());
    assertNotNull(result.getProduct().getName());
    assertNotNull(result.getProduct().getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals(1L, result.getShelvingUnit());
    assertEquals("Side A Test", result.getSide());
    assertEquals(1L, result.getPart());
    assertEquals(1L, result.getShelf());
    assertEquals(2L, result.getProduct().getKey());
    assertEquals("Product Name Test 1", result.getProduct().getName());
    assertEquals(14.50, result.getProduct().getPrice());
  }

  @Test
  @Order(1)
  void findLocationByProductId() {
    //GIVEN
    var service = service();

    //WHEN
    var result = service.findLocationByProductId(2L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getShelvingUnit());
    assertNotNull(result.getSide());
    assertNotNull(result.getShelf());
    assertNotNull(result.getProduct());
    assertNotNull(result.getProduct().getKey());
    assertNotNull(result.getProduct().getName());
    assertNotNull(result.getProduct().getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals(1L, result.getShelvingUnit());
    assertEquals("Side A Test", result.getSide());
    assertEquals(1L, result.getPart());
    assertEquals(1L, result.getShelf());
    assertEquals(2L, result.getProduct().getKey());
    assertEquals("Product Name Test 1", result.getProduct().getName());
    assertEquals(14.50, result.getProduct().getPrice());
  }

  @Test
  @Order(2)
  void subscribingProduct() {
    //GIVEN
    var service = service();

    //WHEN
    var result = service.subscribingProduct(2L, 1L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getShelvingUnit());
    assertNotNull(result.getSide());
    assertNotNull(result.getShelf());
    assertNotNull(result.getProduct());
    assertNotNull(result.getProduct().getKey());
    assertNotNull(result.getProduct().getName());
    assertNotNull(result.getProduct().getPrice());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(2L, result.getKey());
    assertEquals(2L, result.getShelvingUnit());
    assertEquals("Side A Test", result.getSide());
    assertEquals(2L, result.getPart());
    assertEquals(2L, result.getShelf());
    assertEquals(1L, result.getProduct().getKey());
    assertEquals("Product Name Test 2", result.getProduct().getName());
    assertEquals(14.50, result.getProduct().getPrice());
  }

  @Test
  @Order(3)
  void unsubscribingProduct() {
    //GIVEN
    var service = service();

    //WHEN
    var result = service.unsubscribingProduct(1L);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getShelvingUnit());
    assertNotNull(result.getSide());
    assertNotNull(result.getShelf());
    assertNull(result.getProduct());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals(1L, result.getShelvingUnit());
    assertEquals("Side A Test", result.getSide());
    assertEquals(1L, result.getPart());
    assertEquals(1L, result.getShelf());
  }

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ProductEntity.class, Product.class)
        .addMappings(mapper -> {
          mapper.map(ProductEntity::getId, Product::setId);
        });
    modelMapper.typeMap(Product.class, ProductResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Product::getId, ProductResponseDto::setKey);
        });

    modelMapper.typeMap(LocationEntity.class, Location.class)
        .addMappings(mapper -> {
          mapper.map(LocationEntity::getId, Location::setId);
        });
    modelMapper.typeMap(Location.class, LocationResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(Location::getId, LocationResponseDto::setKey);
        });
    return modelMapper;
  }

  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }

  public LocationService service() {
    return new LocationService(
        new FakeLocationOutputPort(),
        new FakeOutputPort(),
        null,
        null,
        entityMapper
    );
  }
}
