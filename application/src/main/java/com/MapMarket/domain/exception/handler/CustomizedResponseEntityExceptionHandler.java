package com.MapMarket.domain.exception.handler;

import com.MapMarket.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public final ResponseEntity<ExceptionResponse> handlerAllExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handlerNotFoundExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ParameterNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handlerParameterNotFoundExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NegativePriceException.class)
  public final ResponseEntity<ExceptionResponse> handlerNegativePriceExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProductCreationException.class)
  public final ResponseEntity<ExceptionResponse> handlerProductCreationExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<ExceptionResponse> handlerBadRequestExceptions(Exception e, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false)
    );

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidJwtAuthenticationException.class)
  public final ResponseEntity<ExceptionResponse> handlerInvalidJwtAuthenticationExceptions(Exception e, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public final ResponseEntity<ExceptionResponse> handlerInvalidCredentialsExceptions(Exception e, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handlerUsernameNotFoundException(Exception e, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        e.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }
}
