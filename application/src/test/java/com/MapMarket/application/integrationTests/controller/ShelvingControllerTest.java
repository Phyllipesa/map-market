package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.ShelvingRequestDto;
import com.MapMarket.application.integrationTests.dto.ShelvingResponseDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
import com.MapMarket.application.integrationTests.dto.wrappers.WrapperShelvingResponseDto;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShelvingControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ShelvingRequestDto shelvingRequest;
  private static ShelvingResponseDto shelvingResponse;
  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    shelvingRequest = new ShelvingRequestDto();
    shelvingRequest.setUnit(5L);
    shelvingRequest.setSideA("Esportes");
    shelvingRequest.setSideB(null);
  }

  @Test
  @Order(0)
  public void authorization() {
    AccountCredentialsDto user = new AccountCredentialsDto("phyllipe", "admin123");

    var accessToken =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(user)
            .when()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(TokenDto.class)
            .getAccessToken();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
        .setBasePath("api/v1/shelvingUnit")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(1)
  public void testFindById() throws JsonProcessingException {
    //GIVEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .when()
              .get("{id}")
            .then()
              .statusCode(200)
                .extract()
                  .body()
                    .asString();

    //WHEN
    shelvingResponse = objectMapper.readValue(content, ShelvingResponseDto.class);

    //THEN
    assertNotNull(shelvingResponse);
    assertNotNull(shelvingResponse.getKey());
    assertNotNull(shelvingResponse.getUnit());
    assertNotNull(shelvingResponse.getSideA());
    assertNotNull(shelvingResponse.getSideB());

    assertTrue(shelvingResponse.getKey() > 0);

    assertEquals(1, shelvingResponse.getUnit());
    assertEquals("Grãos", shelvingResponse.getSideA());
    assertEquals("Farinhas", shelvingResponse.getSideB());
  }

  @Test
  @Order(2)
  public void testCreate() throws JsonProcessingException {
    //GIVEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .when()
              .post()
            .then()
              .statusCode(201)
                .extract()
                  .body()
                    .asString();

    //WHEN
    shelvingResponse = objectMapper.readValue(content, ShelvingResponseDto.class);

    //THEN
    assertNotNull(shelvingResponse);
    assertNotNull(shelvingResponse.getKey());
    assertNotNull(shelvingResponse.getUnit());
    assertNotNull(shelvingResponse.getSideA());
    assertNull(shelvingResponse.getSideB());

    assertTrue(shelvingResponse.getKey() > 0);

    assertEquals(5, shelvingResponse.getUnit());
    assertEquals("Esportes", shelvingResponse.getSideA());
  }

  @Test
  @Order(3)
  public void testFindAll() throws JsonProcessingException {
    //GIVEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .accept(TestConfigs.CONTENT_TYPE_JSON)
            .queryParams("page", 0, "size", 5)
            .when()
              .get()
            .then()
              .statusCode(200)
                .extract()
                  .body()
                    .asString();

    //WHEN
    WrapperShelvingResponseDto wrapper = objectMapper.readValue(content, WrapperShelvingResponseDto.class);
    var response = wrapper.getEmbedded().getShelvings();

    //THEN
    assertNotNull(response);

    ShelvingResponseDto firstShelving = response.get(0);
    assertNotNull(firstShelving);
    assertNotNull(firstShelving.getKey());
    assertNotNull(firstShelving.getUnit());
    assertNotNull(firstShelving.getSideA());
    assertNotNull(firstShelving.getSideB());

    assertEquals(1, firstShelving.getKey());
    assertEquals(1, firstShelving.getUnit());
    assertEquals("Grãos", firstShelving.getSideA());
    assertEquals("Farinhas", firstShelving.getSideB());


    ShelvingResponseDto middleShelving = response.get(2);
    assertNotNull(middleShelving);
    assertNotNull(middleShelving.getKey());
    assertNotNull(middleShelving.getUnit());
    assertNotNull(middleShelving.getSideA());
    assertNotNull(middleShelving.getSideB());

    assertEquals(3, middleShelving.getKey());
    assertEquals(3, middleShelving.getUnit());
    assertEquals("Cereais", middleShelving.getSideA());
    assertEquals("Laticínios", middleShelving.getSideB());

    ShelvingResponseDto lastShelving = response.get(4);
    assertNotNull(lastShelving);
    assertNotNull(lastShelving.getKey());
    assertNotNull(lastShelving.getUnit());
    assertNotNull(lastShelving.getSideA());
    assertNull(lastShelving.getSideB());

    assertEquals(5, lastShelving.getKey());
    assertEquals(5, lastShelving.getUnit());
    assertEquals("Esportes", lastShelving.getSideA());
  }

  @Test
  @Order(4)
  public void testUpdate() throws JsonProcessingException {
    //GIVEN
    shelvingRequest.setSideB("Bebidas");
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .pathParam("id", 5)
            .when()
              .put("{id}")
            .then()
              .statusCode(200)
                .extract()
                  .body()
                    .asString();

    //WHEN
    shelvingResponse = objectMapper.readValue(content, ShelvingResponseDto.class);

    //THEN
    assertNotNull(shelvingResponse);
    assertNotNull(shelvingResponse.getKey());
    assertNotNull(shelvingResponse.getUnit());
    assertNotNull(shelvingResponse.getSideA());
    assertNotNull(shelvingResponse.getSideB());

    assertTrue(shelvingResponse.getKey() > 0);

    assertEquals(5, shelvingResponse.getUnit());
    assertEquals("Esportes", shelvingResponse.getSideA());
    assertEquals("Bebidas", shelvingResponse.getSideB());
  }

  @Test
  @Order(5)
  public void testDelete() {
    //GIVEN
    //WHEN
    //THEN
    given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", 5)
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
  }

  @Test
  @Order(6)
  public void test_HATEAOS() {
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .accept(TestConfigs.CONTENT_TYPE_JSON)
            .queryParams("page", 0, "size", 5)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //THEN
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/shelvingUnit/1\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/shelvingUnit/2\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/shelvingUnit/3\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/shelvingUnit/4\"}}}"));

    assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/v1/shelvingUnit?page=0&size=5\"}"));
    assertTrue(content.contains("\"page\":{\"size\":5,\"totalElements\":4,\"totalPages\":1,\"number\":0}}"));
  }
}
