package com.MapMarket.domain.exception.handler;

import com.MapMarket.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ParameterNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleParameterNotFoundExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NegativePriceException.class)
  public final ResponseEntity<ExceptionResponse> handleNegativePriceExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProductCreationException.class)
  public final ResponseEntity<ExceptionResponse> handleProductCreationExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
