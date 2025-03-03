package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.LocationResponseDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
import com.MapMarket.application.integrationTests.dto.wrappers.WrapperLocationResponseDto;
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
public class LocationControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static LocationResponseDto locationResponse;
  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Test
  @Order(0)
  public void authorization() {
    AccountCredentialsDto user = new AccountCredentialsDto("phyllipe", "admin123");

    var accessToken =
        given()
            .basePath("/auth/sign-in")
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
        .setBasePath("api/v1/location")
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
            .pathParam("id", 7)
            .when()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    locationResponse = objectMapper.readValue(content, LocationResponseDto.class);

    //THEN
    assertNotNull(locationResponse);
    assertNotNull(locationResponse.getKey());
    assertNotNull(locationResponse.getShelvingUnit());
    assertNotNull(locationResponse.getSide());
    assertNotNull(locationResponse.getPart());
    assertNotNull(locationResponse.getShelf());
    assertNotNull(locationResponse.getProduct());
    assertNotNull(locationResponse.getProduct().getKey());
    assertNotNull(locationResponse.getProduct().getName());
    assertNotNull(locationResponse.getProduct().getPrice());

    assertTrue(locationResponse.getKey() > 0);

    assertEquals(1, locationResponse.getShelvingUnit());
    assertEquals("A", locationResponse.getSide());
    assertEquals(2, locationResponse.getPart());
    assertEquals(3, locationResponse.getShelf());
    assertEquals(7, locationResponse.getProduct().getKey());
    assertEquals("Feijão branco", locationResponse.getProduct().getName());
    assertEquals(8.00, locationResponse.getProduct().getPrice());
  }

  @Test
  @Order(2)
  public void testFindAll() throws JsonProcessingException {
    //GIVEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .accept(TestConfigs.CONTENT_TYPE_JSON)
            .queryParams("page", 0, "size", 10)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    WrapperLocationResponseDto wrapper = objectMapper.readValue(content, WrapperLocationResponseDto.class);
    var response = wrapper.getEmbedded().getLocations();

    //THEN
    assertNotNull(response);

    locationResponse = response.get(0);
    assertNotNull(locationResponse);
    assertNotNull(locationResponse.getKey());
    assertNotNull(locationResponse.getShelvingUnit());
    assertNotNull(locationResponse.getSide());
    assertNotNull(locationResponse.getPart());
    assertNotNull(locationResponse.getShelf());
    assertNotNull(locationResponse.getProduct());
    assertNotNull(locationResponse.getProduct().getKey());
    assertNotNull(locationResponse.getProduct().getName());
    assertNotNull(locationResponse.getProduct().getPrice());

    assertTrue(locationResponse.getKey() > 0);

    assertEquals(1, locationResponse.getShelvingUnit());
    assertEquals("A", locationResponse.getSide());
    assertEquals(1, locationResponse.getPart());
    assertEquals(1, locationResponse.getShelf());

    assertEquals(1, locationResponse.getProduct().getKey());
    assertEquals("Arroz branco", locationResponse.getProduct().getName());
    assertEquals(4.50, locationResponse.getProduct().getPrice());

    locationResponse = response.get(9);
    assertNotNull(locationResponse);
    assertNotNull(locationResponse.getKey());
    assertNotNull(locationResponse.getShelvingUnit());
    assertNotNull(locationResponse.getSide());
    assertNotNull(locationResponse.getPart());
    assertNotNull(locationResponse.getShelf());
    assertNotNull(locationResponse.getProduct());
    assertNotNull(locationResponse.getProduct().getKey());
    assertNotNull(locationResponse.getProduct().getName());
    assertNotNull(locationResponse.getProduct().getPrice());

    assertTrue(locationResponse.getKey() > 0);

    assertEquals(1, locationResponse.getShelvingUnit());
    assertEquals("A", locationResponse.getSide());
    assertEquals(3, locationResponse.getPart());
    assertEquals(2, locationResponse.getShelf());

    assertEquals(10, locationResponse.getProduct().getKey());
    assertEquals("Ervilha seca", locationResponse.getProduct().getName());
    assertEquals(5.50, locationResponse.getProduct().getPrice());
  }
  @Test
  @Order(3)
  public void unsubscribingProduct() throws JsonProcessingException {
    //GIVEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 128)
            .when()
            .put("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    locationResponse = objectMapper.readValue(content, LocationResponseDto.class);

    //THEN
    assertNotNull(locationResponse);
    assertNotNull(locationResponse.getKey());
    assertNotNull(locationResponse.getShelvingUnit());
    assertNotNull(locationResponse.getSide());
    assertNotNull(locationResponse.getPart());
    assertNotNull(locationResponse.getShelf());
    assertNull(locationResponse.getProduct());

    assertTrue(locationResponse.getKey() > 0);

    assertEquals(4, locationResponse.getShelvingUnit());
    assertEquals("B", locationResponse.getSide());
    assertEquals(4, locationResponse.getPart());
    assertEquals(4, locationResponse.getShelf());
  }

  @Test
  @Order(4)
  public void subscribingProduct() throws JsonProcessingException {
    //GIVEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("locationId", 127)
            .pathParam("productId", 127)
            .when()
            .put("{locationId}/{productId}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    locationResponse = objectMapper.readValue(content, LocationResponseDto.class);

    //THEN
    assertNotNull(locationResponse);
    assertNotNull(locationResponse.getKey());
    assertNotNull(locationResponse.getShelvingUnit());
    assertNotNull(locationResponse.getSide());
    assertNotNull(locationResponse.getPart());
    assertNotNull(locationResponse.getShelf());
    assertNotNull(locationResponse.getProduct());
    assertNotNull(locationResponse.getProduct().getKey());
    assertNotNull(locationResponse.getProduct().getName());
    assertNotNull(locationResponse.getProduct().getPrice());

    assertTrue(locationResponse.getKey() > 0);

    assertEquals(4, locationResponse.getShelvingUnit());
    assertEquals("B", locationResponse.getSide());
    assertEquals(4, locationResponse.getPart());
    assertEquals(3, locationResponse.getShelf());
    assertEquals(127, locationResponse.getProduct().getKey());
    assertEquals("Saco de lixo", locationResponse.getProduct().getName());
    assertEquals(4.00, locationResponse.getProduct().getPrice());
  }

  @Test
  @Order(5)
  public void test_HATEAOS() {
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .accept(TestConfigs.CONTENT_TYPE_JSON)
            .queryParams("page", 0, "size", 10)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
    //THEN
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/location/1\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/location/5\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/location/10\"}}}"));

    assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/v1/location?page=0&size=10&sort=id,asc\"}"));
    assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/v1/location?page=0&size=10\"}"));
    assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/v1/location?page=1&size=10&sort=id,asc\"}"));
    assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/v1/location?page=12&size=10&sort=id,asc\"}}"));

    assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":127,\"totalPages\":13,\"number\":0}}"));
  }
}