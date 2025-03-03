package com.MapMarket.application.integrationTests.controller.exceptions;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.dto.ProductRequestDto;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
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
public class ProductControllerExceptionTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ProductRequestDto productRequest;

  @BeforeEach
  public void setup() {
    productRequest = new ProductRequestDto();
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
        .setBasePath("api/v1/product")
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
    assertTrue(content.contains("Product not found with id 200"));
  }

  @Test
  @Order(2)
  public void test_findAll_WITHOUT_TOKEN() {
    //WHEN
    RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
        .setBasePath("api/v1/product")
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

  @Test
  @Order(3)
  public void test_create_WITH_PARAMETER_name_NULL() {
    //GIVEN
    productRequest.setPrice(14.22);

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'name' is null or blank!"));
  }

  @Test
  @Order(4)
  public void test_create_WITH_PARAMETER_price_NULL() {
    //GIVEN
    productRequest.setName("Test");

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'price' is null or blank!"));
  }

  @Test
  @Order(5)
  public void test_create_WITH_PARAMETER_name_BLANK() {
    //GIVEN
    productRequest.setName("");
    productRequest.setPrice(14.20);

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'name' is null or blank!"));
  }

  @Test
  @Order(6)
  public void test_create_PARAMETER_price_WITH_NEGATIVE_NUMBER() {
    //GIVEN
    productRequest.setName("Test");
    productRequest.setPrice(-1.0);

    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(productRequest)
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
  @Order(7)
  public void test_create_WITH_NULL_REQUEST() {
    //GIVEN
    //WHEN
    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body("")
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Failed to read request"));
  }

  @Test
  @Order(8)
  public void test_update_NOT_FOUND() {
    //GIVEN
    productRequest.setName("Test");
    productRequest.setPrice(14.20);
    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 200)
            .body(productRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Product not found with id 200"));
  }

  @Test
  @Order(9)
  public void test_update_WITH_PARAMETER_name_NULL() {
    //GIVEN
    productRequest.setPrice(14.20);

    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(productRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'name' is null or blank!"));
  }

  @Test
  @Order(10)
  public void test_update_WITH_PARAMETER_price_NULL() {
    //GIVEN
    productRequest.setName("Test");

    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(productRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'price' is null or blank!"));
  }

  @Test
  @Order(11)
  public void test_update_WITH_PARAMETER_name_BLANK() {
    //GIVEN
    productRequest.setName("");
    productRequest.setPrice(14.20);

    //WHEN
    var content =
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("id", 1)
            .body(productRequest)
            .when()
            .put("{id}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'name' is null or blank!"));
  }

  @Test
  @Order(12)
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
    assertTrue(content.contains("Product not found with id 200"));
  }
}
