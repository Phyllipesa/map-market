package com.MapMarket.application.unitTests.mockito.services;

import com.MapMarket.application.rest.responseDto.ShelvingResponseDto;
import com.MapMarket.application.unitTests.fakeClasses.FakeShelvingOutputPort;
import com.MapMarket.application.unitTests.mocks.MockShelving;
import com.MapMarket.domain.logic.ShelvingValidator;
import com.MapMarket.domain.models.ShelvingUnit;
import com.MapMarket.domain.service.ShelvingService;
import com.MapMarket.infrastructure.adapters.output.persistence.entities.ShelvingUnitEntity;
import com.MapMarket.infrastructure.adapters.output.persistence.mapper.EntityMapper;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShelvingServiceTest {

  private MockShelving mockShelving;
  private final EntityMapper entityMapper = entityMapper();

  @BeforeEach
  void setUpMocks() {
    mockShelving = new MockShelving();
    MockitoAnnotations.openMocks(this);
  }

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
    assertNotNull(result.getUnit());
    assertNotNull(result.getSideA());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(1L, result.getKey());
    assertEquals(1L, result.getUnit());
    assertEquals("Test side A", result.getSideA());
    assertEquals("Test side B", result.getSideB());
  }

  @Test
  @Order(1)
  void create() {
    //GIVEN
    var service = service();
    var request = mockShelving.mockRequestDto();

    //WHEN
    var result = service.create(request);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getUnit());
    assertNotNull(result.getSideA());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(2L, result.getKey());
    assertEquals(2L, result.getUnit());
    assertEquals("Test side A", result.getSideA());
    assertNull(result.getSideB());
  }

  @Test
  @Order(2)
  void update() {
    //GIVEN
    var request = mockShelving.mockRequestDto();
    var service = service();

    //WHEN
    var result = service.update(3L, request);

    //THEN
    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getUnit());
    assertNotNull(result.getSideA());
    assertNotNull(result.getLinks());

    assertTrue(result.getLinks().toString().contains("self"));

    assertEquals(3L, result.getKey());
    assertEquals(2L, result.getUnit());
    assertEquals("Test side A", result.getSideA());
    assertEquals("Test side B", result.getSideB());
  }

  @Test
  @Order(3)
  void delete() {
    //GIVEN
    FakeShelvingOutputPort fake = mock(FakeShelvingOutputPort.class);
    var service = new ShelvingService(
        fake,
        null,
        null,
        null,
        null
    );

    //WHEN
    when(fake.existResource(3L)).thenReturn(true);
    service.delete(3L);

    //THEN
    verify(fake, times(1)).existResource(3L);
    verify(fake, times(1)).delete(3L);
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