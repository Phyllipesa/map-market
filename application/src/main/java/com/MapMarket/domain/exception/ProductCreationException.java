package com.MapMarket.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductCreationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public ProductCreationException(String e) {
    super(e);
  }

  public ProductCreationException(String e, Throwable cause) {
    super(e, cause);
  }
}