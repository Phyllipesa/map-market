package com.MapMarket.application.integrationTests.controller;

import com.MapMarket.application.configs.TestConfigs;
import com.MapMarket.application.integrationTests.testContainers.AbstractIntegrationTest;
import com.MapMarket.application.integrationTests.vo.AccountCredentialsVO;
import com.MapMarket.application.integrationTests.vo.ProdutoRequestDto;
import com.MapMarket.application.integrationTests.vo.ProdutoResponseDto;
import com.MapMarket.application.integrationTests.vo.TokenVO;
import com.MapMarket.application.integrationTests.vo.wrappers.WrapperProdutoResponseDto;
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
  private static ProdutoRequestDto productRequest;
  private static ProdutoResponseDto productResponse;
  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    productRequest = new ProdutoRequestDto();
    productRequest.setNome("Lentilha");
    productRequest.setPreco(14.20);
  }

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
  public void testCreate() throws JsonProcessingException {
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

    productResponse = objectMapper.readValue(content, ProdutoResponseDto.class);

    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getNome());
    assertNotNull(productResponse.getPreco());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Lentilha", productResponse.getNome());
    assertEquals(14.20, productResponse.getPreco());
  }

  @Test
  @Order(2)
  public void testFindById() throws JsonProcessingException {
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

    productResponse = objectMapper.readValue(content, ProdutoResponseDto.class);

    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getNome());
    assertNotNull(productResponse.getPreco());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Lentilha", productResponse.getNome());
    assertEquals(14.20, productResponse.getPreco());
  }

  @Test
  @Order(3)
  public void testFindAll() throws JsonProcessingException {
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

    WrapperProdutoResponseDto wrapper = objectMapper.readValue(content, WrapperProdutoResponseDto.class);
    var response = wrapper.getEmbedded().getProdutos();

    assertNotNull(response);

    ProdutoResponseDto firstProduct = response.get(0);
    assertNotNull(firstProduct);
    assertNotNull(firstProduct.getKey());
    assertNotNull(firstProduct.getNome());
    assertNotNull(firstProduct.getPreco());

    assertEquals(18, firstProduct.getKey());
    assertEquals("Bolacha recheada", firstProduct.getNome());
    assertEquals(2.80, firstProduct.getPreco());


    ProdutoResponseDto LastProduct = response.get(2);
    assertNotNull(LastProduct);
    assertNotNull(LastProduct.getKey());
    assertNotNull(LastProduct.getNome());
    assertNotNull(LastProduct.getPreco());

    assertEquals(7, LastProduct.getKey());
    assertEquals("Café em pó", LastProduct.getNome());
    assertEquals(8.20, LastProduct.getPreco());
  }

  @Test
  @Order(4)
  public void testUpdate() throws JsonProcessingException {
    productRequest.setNome("Castanha do vamo para");
    productRequest.setPreco(15.00);

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

    productResponse = objectMapper.readValue(content, ProdutoResponseDto.class);

    assertNotNull(productResponse);
    assertNotNull(productResponse.getKey());
    assertNotNull(productResponse.getNome());
    assertNotNull(productResponse.getPreco());

    assertTrue(productResponse.getKey() > 0);

    assertEquals("Castanha do vamo para", productResponse.getNome());
    assertEquals(15.00, productResponse.getPreco());
  }

  @Test
  @Order(5)
  public void testDelete() throws JsonProcessingException {
    given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", 73)
        .when()
        .delete("{id}")
        .then()
        .statusCode(204);
  }
}
