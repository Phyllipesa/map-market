package com.MapMarket.domain.exception.constants;

public class Constant {
  public static final String THIS_PRODUCT_IS_ALREADY_REGISTERED = "This Product is already registered!";
  public static final String THIS_LOCATION_WITH_PRODUCT_REGISTERED = "There is already a product registered in location with id ";
  public static final String ERROR_CREATING_PRODUCT = "Error occurred while creating the product";
  public static final String ERROR_CREATING_SHELVING_UNIT = "Error occurred while creating the product";
  public static final String ERROR_PRODUCT_IN_LOCATION_NOT_FOUND = "There is no location with the product id ";
  public static final String NEGATIVE_NOT_ALLOWED = "It is not allowed negative numbers!";
  public static final String PRODUCTS_NOT_FOUND = "Products not found!";
  public static final String LOCATIONS_NOT_FOUND = "Locations not found!";
  public static final String SHELVING_UNITS_NOT_FOUND = "Shelving units not found!";
  public static final String PRODUCT_NOT_FOUND = "Product not found with id ";
  public static final String LOCATION_NOT_FOUND = "Location not found with id ";
  public static final String SHELVING_NOT_FOUND = "Shelving not found with id ";

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
