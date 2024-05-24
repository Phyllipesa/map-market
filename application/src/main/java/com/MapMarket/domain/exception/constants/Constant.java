package com.MapMarket.domain.exception.constants;

public class Constant {
  public static final String THIS_PRODUCT_IS_ALREADY_REGISTERED = "This Product is already registered ";
  public static final String ERROR_CREATING_PRODUCT = "Error occurred while creating the product";
  public static final String NULL_NOT_ALLOWED = "It is not allowed to persist a null object!";
  public static final String NEGATIVE_NOT_ALLOWED = "It is not allowed negative numbers!";
  public static final String PRODUCT_NOT_FOUND = "Product not found with id ";
  public static final double D_2 = 0.0;
  public static final int INT = 0;

  public static final String INVALID_USER_OR_PASSWORD = "Invalid username/password supplied!";
  public static final String EXPIRED_OR_INVALID_JWT_TOKEN = "Expired or invalid JWT token!";
  public static final String USERNAME_NOT_FOUND = "Username not found: ";

  private Constant() {
  }

  public static String requiredParameterMessage(String parameterName) {
    return "Required parameter '" + parameterName + "' is null or blank!";
  }

  public static String requiredCredentialsMessage(String info) {
    return "Credentials '" + info + "' is null or blank!";
  }
}
