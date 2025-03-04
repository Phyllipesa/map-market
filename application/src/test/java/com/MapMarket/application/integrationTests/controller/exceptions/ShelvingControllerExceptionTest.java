package com.MapMarket.application.integrationTests.controller.exceptions;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.ShelvingRequestDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShelvingControllerExceptionTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ShelvingRequestDto shelvingRequest;

  @BeforeEach
  public void setup() {
    shelvingRequest = new ShelvingRequestDto();
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
        .setBasePath("api/v1/shelvingUnit")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(1)
  public void test_findById_NOT_FOUND() {
    //WHEN
    var content =
        given().spec(specification)
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
    assertTrue(content.contains("Shelving not found with id 200"));
  }

  @Test
  @Order(2)
  public void test_create_WITH_PARAMETER_unit_NULL() {
    //GIVEN
    shelvingRequest.setSideA("Test");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'unit' is null or blank!"));
  }

  @Test
  @Order(3)
  public void test_create_WITH_PARAMETER_sideA_NULL() {
    //GIVEN
    shelvingRequest.setUnit(1L);

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'sideA' is null or blank!"));
  }

  @Test
  @Order(4)
  public void test_create_WITH_PARAMETER_sideA_BLANK() {
    //GIVEN
    shelvingRequest.setUnit(1L);
    shelvingRequest.setSideA("");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'sideA' is null or blank!"));
  }

  @Test
  @Order(5)
  public void test_create_PARAMETER_unit_WITH_NEGATIVE_NUMBER() {
    //GIVEN
    shelvingRequest.setUnit(-1L);
    shelvingRequest.setSideA("Test");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("It is not allowed negative numbers!"));
  }

  @Test
  @Order(6)
  public void test_update_NOT_FOUND() {
    //GIVEN
    shelvingRequest.setUnit(1L);
    shelvingRequest.setSideA("Test");

    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(shelvingRequest)
            .pathParam("id", 200)
            .when()
            .put("{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Shelving not found with id 200"));
  }

  @Test
  @Order(7)
  public void test_update_WITH_PARAMETER_unit_NULL() {
    //GIVEN
    shelvingRequest.setSideA("Test");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(shelvingRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'unit' is null or blank!"));
  }

  @Test
  @Order(8)
  public void test_update_WITH_PARAMETER_sideA_NULL() {
    //GIVEN
    shelvingRequest.setUnit(1L);

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(shelvingRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'sideA' is null or blank!"));
  }

  @Test
  @Order(9)
  public void test_update_WITH_PARAMETER_sideA_BLANK() {
    //GIVEN
    shelvingRequest.setUnit(1L);
    shelvingRequest.setSideA("");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(shelvingRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'sideA' is null or blank!"));
  }

  @Test
  @Order(10)
  public void test_delete_NOT_FOUND() {
    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 200)
            .when()
            .delete("{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Shelving not found with id 200"));
  }
}
