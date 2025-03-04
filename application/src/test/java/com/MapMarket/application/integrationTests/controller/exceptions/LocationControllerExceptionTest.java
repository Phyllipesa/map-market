package com.MapMarket.application.integrationTests.controller.exceptions;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationControllerExceptionTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;

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
        .setBasePath("api/v1/location")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(1)
  public void test_findById_LOCATION_NOT_FOUND() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 200)
            .when()
            .get("{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Location not found with id 200"));
  }

  @Test
  @Order(2)
  public void test_findLocationByProductId_ERROR_PRODUCT_IN_LOCATION_NOT_FOUND() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 200)
            .when()
            .get("/product/{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("There is no location with the product id 200"));
  }

  @Test
  @Order(3)
  public void test_subscribingProduct_THIS_PRODUCT_IS_ALREADY_REGISTERED() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("locationId", 128)
            .pathParam("productId", 1)
            .when()
            .put("{locationId}/{productId}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("This Product is already registered!"));
  }

  @Test
  @Order(4)
  public void test_subscribingProduct_THIS_LOCATION_WITH_PRODUCT_REGISTERED() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("locationId", 1)
            .pathParam("productId", 128)
            .when()
            .put("{locationId}/{productId}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("There is already a product registered in location with id 1"));
  }

  @Test
  @Order(5)
  public void test_subscribingProduct_LOCATION_NOT_FOUND() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("locationId", 130)
            .pathParam("productId", 130)
            .when()
            .put("{locationId}/{productId}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Location not found with id 130"));
  }

  @Test
  @Order(6)
  public void test_subscribingProduct_PRODUCT_NOT_FOUND() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("locationId", 128)
            .pathParam("productId", 131)
            .when()
            .put("{locationId}/{productId}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Product not found with id 131"));
  }

  @Test
  @Order(7)
  public void test_unsubscribingProduct_LOCATION_NOT_FOUND() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 130)
            .when()
            .put("{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Location not found with id 130"));
  }

  @Test
  @Order(8)
  public void test_findAll_WITHOUT_TOKEN() {
    //WHEN
    RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
        .setBasePath("api/v1/location")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    //THEN
    given()
        .spec(specificationWithoutToken)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .when()
        .get()
        .then()
        .statusCode(403);
  }
}
