package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
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

  @Test
  @Order(0)
  public void test_signin_WITH_PARAMETER_username_NULL() {
    String payloadWithNullPass = "{\"password\": \"blabla\"}";

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

    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }

  @Test
  @Order(1)
  public void test_signin_WITH_PARAMETER_password_NULL() {
    String payloadWithNullPass = "{\"username\": \"blabla1\"}";

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

    assertNotNull(content);
    assertTrue(content.contains("Credentials 'username/password' is null or blank!"));
  }
}
