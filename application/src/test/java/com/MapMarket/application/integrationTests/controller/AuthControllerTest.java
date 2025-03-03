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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest extends AbstractIntegrationTest {

  private static TokenDto tokenDto;

  @Test
  @Order(1)
  public void testSignIn() {
    //GIVEN
    AccountCredentialsDto user = new AccountCredentialsDto("phyllipe", "admin123");

    //WHEN
    tokenDto = given()
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
                .as(TokenDto.class);

    //THEN
    assertNotNull(tokenDto.getAccessToken());
    assertNotNull(tokenDto.getRefreshToken());
  }

  @Test
  @Order(2)
  public void testRefresh() {
    //GIVEN
    AccountCredentialsDto user = new AccountCredentialsDto("phyllipe", "admin123");

    //WHEN
    var newtokenVO = given()
        .basePath("/auth/refresh")
        .port(TestConfigs.SERVER_PORT)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
          .pathParam("username", tokenDto.getUsername())
          .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
        .when()
          .put("{username}")
        .then()
          .statusCode(200)
            .extract()
              .body()
                .as(TokenDto.class);

    //THEN
    assertNotNull(newtokenVO.getAccessToken());
    assertNotNull(newtokenVO.getRefreshToken());
  }
}
