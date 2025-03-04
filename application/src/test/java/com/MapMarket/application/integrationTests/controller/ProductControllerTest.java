package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.ProductRequestDto;
import com.MapMarket.application.integrationTests.dto.ProductResponseDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
import com.MapMarket.application.integrationTests.dto.wrappers.WrapperProductResponseDto;
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
public class ProductControllerTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ProductRequestDto productRequest;
  private static ProductResponseDto productResponse;
  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    productRequest = new ProductRequestDto();
    productRequest.setName("Lentilha");
    productRequest.setPrice(14.20);
  }

  @Test
  @Order(0)
  public void authorization() {
    AccountCredentialsDto user = new AccountCredentialsDto("john", "admin123");

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
        .setBasePath("api/v1/product")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(1)
  public void testCreate() throws JsonProcessingException {
    //GIVEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
            .when()
            .post()
            .then()
            .statusCode(201)
            .extract()
            .body()
            .asString();

    //WHEN
    productResponse = objectMapper.readValue(content, ProductResponseDto.class);

    //THEN
    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getName());
    assertNotNull(productResponse.getPrice());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Lentilha", productResponse.getName());
    assertEquals(14.20, productResponse.getPrice());
  }

  @Test
  @Order(2)
  public void testFindById() throws JsonProcessingException {
    //GIVEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 73)
            .when()
            .get("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    productResponse = objectMapper.readValue(content, ProductResponseDto.class);

    //THEN
    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getName());
    assertNotNull(productResponse.getPrice());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Cereal matinal integral", productResponse.getName());
    assertEquals(6.00, productResponse.getPrice());
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
            .queryParams("page", 3, "size", 3)
          .when()
            .get()
          .then()
            .statusCode(200)
              .extract()
                .body()
                  .asString();

    //WHEN
    WrapperProductResponseDto wrapper = objectMapper.readValue(content, WrapperProductResponseDto.class);
    var response = wrapper.getEmbedded().getProducts();

    //THEN
    assertNotNull(response);

    productResponse = response.get(0);
    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getName());
    assertNotNull(productResponse.getPrice());

    assertEquals(56, productResponse.getKey());
    assertEquals("Balas de goma", productResponse.getName());
    assertEquals(3.50, productResponse.getPrice());


    productResponse = response.get(2);
    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getName());
    assertNotNull(productResponse.getPrice());

    assertEquals(53, productResponse.getKey());
    assertEquals("Barra de cereais", productResponse.getName());
    assertEquals(3.00, productResponse.getPrice());
  }

  @Test
  @Order(4)
  public void testUpdate() throws JsonProcessingException {
    //GIVEN
    productRequest.setName("Castanha do vamo para");
    productRequest.setPrice(15.00);

    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
            .pathParam("id", 73)
            .when()
            .put("{id}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //WHEN
    productResponse = objectMapper.readValue(content, ProductResponseDto.class);

    //THEN
    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getName());
    assertNotNull(productResponse.getPrice());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Castanha do vamo para", productResponse.getName());
    assertEquals(15.00, productResponse.getPrice());
  }

  @Test
  @Order(5)
  public void testDelete() {
    //GIVEN
    //WHEN
    //THEN
    given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", 129)
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
            .queryParams("page", 0, "size", 10)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

    //THEN
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/product/117\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/product/116\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/product/1\"}}}"));
    assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/v1/product/66\"}}}"));

    assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/api/v1/product?page=0&size=10&sort=name,asc\"}"));
    assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/api/v1/product?page=0&size=10\"}"));
    assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/api/v1/product?page=1&size=10&sort=name,asc\"}"));
    assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/api/v1/product?page=12&size=10&sort=name,asc\"}}"));

    assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":129,\"totalPages\":13,\"number\":0}}"));
  }
}
