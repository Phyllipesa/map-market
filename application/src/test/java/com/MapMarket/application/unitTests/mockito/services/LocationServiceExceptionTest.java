package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.responseDto.LocationResponseDto;
import com.MapMarket.application.rest.responseDto.ProductResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeLocationOutputPort;
import com.MapMarket.application.unitTests.fakeClasses.FakeOutputPort;
import com.MapMarket.application.unitTests.mocks.MockLocation;
import com.MapMarket.domain.exception.ProductAlreadyAssignedException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.models.Location;
import com.MapMarket.domain.models.Product;
import com.MapMarket.domain.service.LocationService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.LocationEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProductEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationServiceExceptionTest {

  MockLocation mockLocation;

  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    mockLocation = new MockLocation();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Order(0)
  void findById_RESOURCE_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.LOCATION_NOT_FOUND + 3L;

    //WHEN
    Exception exception = assertThrows(
        ResourceNotFoundException.class, () -> service.findById(3L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(1)
  void findLocationByProductId_RESOURCE_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.ERROR_PRODUCT_IN_LOCATION_NOT_FOUND + 3L;

    //WHEN
    Exception exception = assertThrows(
        ResourceNotFoundException.class, () -> service.findLocationByProductId(3L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(2)
  void subscribingProduct_PRODUCT_IS_ALREADY_REGISTERED() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.THIS_PRODUCT_IS_ALREADY_REGISTERED;

    //WHEN
    Exception exception = assertThrows(
        ProductAlreadyAssignedException.class, () -> service.subscribingProduct(1L, 1L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(3)
  void subscribingProduct_LOCATION_WITH_PRODUCT_REGISTERED() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.THIS_LOCATION_WITH_PRODUCT_REGISTERED;

    //WHEN
    Exception exception = assertThrows(
        ProductAlreadyAssignedException.class, () -> service.subscribingProduct(1L, 3L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(4)
  void subscribingProduct_LOCATION_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.LOCATION_NOT_FOUND + 3L;

    //WHEN
    Exception exception = assertThrows(
        ResourceNotFoundException.class, () -> service.subscribingProduct(3L, 3L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(5)
  void subscribingProduct_PRODUCT_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.PRODUCT_NOT_FOUND + 4L;

    //WHEN
    Exception exception = assertThrows(
        ResourceNotFoundException.class, () -> service.subscribingProduct(2L, 4L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(6)
  void unsubscribingProduct_RESOURCE_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.LOCATION_NOT_FOUND + 3L;

    //WHEN
    Exception exception = assertThrows(
        ResourceNotFoundException.class, () -> service.unsubscribingProduct(3L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
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
