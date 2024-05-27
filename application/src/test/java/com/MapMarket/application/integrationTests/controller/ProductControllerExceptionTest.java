package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.MapMarket.application.integrationTests.vo.AccountCredentialsVO;
import com.MapMarket.application.integrationTests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class ProductControllerExceptionTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;

  @Test
  @Order(0)
  public void authorization() throws JsonProcessingException {
    AccountCredentialsVO user = new AccountCredentialsVO("phyllipe", "admin123");

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
            .as(TokenVO.class)
            .getAccessToken();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
        .setBasePath("api/v1/produto")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }


  @Test
  @Order(1)
  public void test_create_WITH_PARAMETER_name_NULL() {
    String payloadNomeAusente = "{\"preco\": \"14.20\"}";

    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadNomeAusente)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'nome' is null or blank!"));
  }

  @Test
  @Order(2)
  public void test_create_WITH_PARAMETER_price_NULL() {
    String payloadPrecoAusente = "{\"nome\": \"Lentilha\"}";

    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadPrecoAusente)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'preco' is null or blank!"));
  }

  @Test
  @Order(3)
  public void test_create_WITH_PARAMETER_name_BLANK() {
    String payloadNomeEmBranco = "{\"nome\": \"\", \"preco\": \"14.20\"}";

    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadNomeEmBranco)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'nome' is null or blank!"));
  }

  @Test
  @Order(4)
  public void test_create_WITH_PARAMETER_price_BLANK() {
    String payloadPrecoEmBranco = "{\"nome\": \"Lentilha\", \"preco\": \"\"}";

    var content =
        given()
            .spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadPrecoEmBranco)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    assertNotNull(content);
    assertTrue(content.contains("Required parameter 'preco' is null or blank!"));
  }

  @Test
  @Order(5)
  public void test_create_WITH_NULL_REQUEST() {
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

    assertNotNull(content);
    assertTrue(content.contains("Failed to read request"));
  }
}
