package com.MapMarket.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegativePriceException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public NegativePriceException(String e) {
    super(e);
  }

  public NegativePriceException(String e, Throwable cause) {
    super(e, cause);
  }
}