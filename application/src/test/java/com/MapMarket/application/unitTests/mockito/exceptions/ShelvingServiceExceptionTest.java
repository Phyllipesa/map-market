package com.MapMarket.application.unitTests.mockito.exceptions;

import com.MapMarket.application.rest.requestDto.ShelvingRequestDto;
import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeShelvingOutputPort;
import com.MapMarket.application.unitTests.mocks.MockShelving;
import com.MapMarket.domain.exception.ParameterNotFoundException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.ShelvingUnitCreationException;
import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.logic.ShelvingValidator;
import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.service.ShelvingService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShelvingServiceExceptionTest {

  MockShelving mockShelving;
  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    mockShelving = new MockShelving();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Order(0)
  void findById_RESOURCE_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.SHELVING_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.findById(2L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(1)
  void create_PARAMETER_unit_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.requiredParameterMessage("unit");
    ShelvingRequestDto requestDto = mockShelving.mockRequestDto();
    requestDto.setUnit(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> service.create(requestDto));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(2)
  void create_PARAMETER_sideA_NOT_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.requiredParameterMessage("sideA");
    ShelvingRequestDto requestDto = mockShelving.mockRequestDto();
    requestDto.setSideA(null);

    //WHEN
    Exception exception = assertThrows(ParameterNotFoundException.class, () -> service.create(requestDto));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(3)
  void create_ERROR_CREATING_SHELVING_UNIT() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.ERROR_CREATING_SHELVING_UNIT;
    ShelvingRequestDto requestDto = mockShelving.mockRequestDto();
    requestDto.setSideA("Exception");
    //WHEN
    Exception exception = assertThrows(ShelvingUnitCreationException.class, () -> service.create(requestDto));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(4)
  void update_RESOURCE_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.SHELVING_NOT_FOUND + 2L;
    ShelvingRequestDto requestDto = mockShelving.mockRequestDto();

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.update(2L, requestDto));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(5)
  void delete_RESOURCE_FOUND() {
    //GIVEN
    var service = service();
    String expectedMessage = Constant.SHELVING_NOT_FOUND + 2L;

    //WHEN
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(2L));
    String actualMessage = exception.getMessage();

    //THEN
    assertTrue(actualMessage.contains(expectedMessage));
  }

  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(ShelvingUnitEntity.class, ShelvingUnit.class)
        .addMappings(mapper -> {
          mapper.map(ShelvingUnitEntity::getId, ShelvingUnit::setId);
        });
    modelMapper.typeMap(ShelvingUnit.class, ShelvingResponseDto.class)
        .addMappings(mapper -> {
          mapper.map(ShelvingUnit::getId, ShelvingResponseDto::setKey);
        });
    return modelMapper;
  }

  public EntityMapper entityMapper() {
    return new EntityMapper(modelMapper());
  }

  public ShelvingService service() {
    return new ShelvingService(
        new FakeShelvingOutputPort(),
        null,
        null,
        new ShelvingValidator(),
        entityMapper
    );
  }
}
