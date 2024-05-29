package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.MapMarket.application.integrationTests.dto.AccountCredentialsDto;
import com.MapMarket.application.integrationTests.dto.TokenDto;
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
public class AuthControllerExceptionTest extends AbstractIntegrationTest {

  private static TokenDto tokenDto;

  @Test
  @Order(0)
  public void test_signing() {
    //GIVEN
    AccountCredentialsDto user = new AccountCredentialsDto("phyllipe", "admin123");

    //WHEN
    tokenDto = given()
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
        .as(TokenDto.class);

    //THEN
    assertNotNull(tokenDto.getAccessToken());
    assertNotNull(tokenDto.getRefreshToken());
  }

  @Test
  @Order(1)
  public void test_signing_WITH_NULL_CREDENTIALS() {
    //GIVEN

    //WHEN
    var content =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
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
  @Order(2)
  public void test_signing_WITH_PARAMETER_username_NULL() {
    //GIVEN
    String payloadWithNullPass = "{\"password\": \"blabla\"}";

    //WHEN
    var content =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadWithNullPass)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }

  @Test
  @Order(3)
  public void test_signing_WITH_PARAMETER_password_NULL() {
    //GIVEN
    String payloadWithNullPass = "{\"username\": \"blabla1\"}";

    //WHEN
    var content =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadWithNullPass)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }

  @Test
  @Order(4)
  public void test_signing_WITH_PARAMETER_username_BLANK() {
    //GIVEN
    String payloadWithNullPass = "{\"username\": \"\", \"password\": \"blabla\"}";

    //WHEN
    var content =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadWithNullPass)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }

  @Test
  @Order(5)
  public void test_signing_WITH_PARAMETER_password_BLANK() {
    //GIVEN
    String payloadWithNullPass = "{\"username\": \"blabla1\", \"password\": \"\"}";

    //WHEN
    var content =
        given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(payloadWithNullPass)
            .when()
            .post()
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }

  @Test
  @Order(6)
  public void test_RefreshToken_WITH_WRONG_USER_AND_WITH_TOKEN() {
    //WHEN
    var content =
        given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", "bender")
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, tokenDto.getAccessToken())
            .when()
            .put("{username}")
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Username not found: bender"));
  }

  @Test
  @Order(7)
  public void test_RefreshToken_WITH_BLANK_USER_AND_WITH_TOKEN() {
    //WHEN
    var content =
        given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", " ")
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, tokenDto.getAccessToken())
            .when()
            .put("{username}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/token' is null or blank!"));
  }

  @Test
  @Order(8)
  public void test_RefreshToken_WITH_NULL_USER_AND_WITH_TOKEN() {
    //WHEN
    var content =
        given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, tokenDto.getAccessToken())
            .when()
            .put()
            .then()
            .statusCode(404)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("No static resource auth/refresh."));
  }

  @Test
  @Order(9)
  public void test_RefreshToken_WITH_USER_AND_TOKEN_BLANK() {
    //WHEN
    var content =
        given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", "cmUSER")
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, " ")
            .when()
            .put("{username}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/token' is null or blank!"));
  }

  @Test
  @Order(10)
  public void test_RefreshToken_WITH_USER_AND_TOKEN_NULL() {
    //WHEN
    var content =
        given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", "cmUSER")
            .when()
            .put("{username}")
            .then()
            .statusCode(400)
            .extract()
            .body()
            .asString();

    //THEN
    assertNotNull(content);
    assertTrue(content.contains("Required header 'Authorization' is not present."));
  }
}
