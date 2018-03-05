package com.exchange.rates.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BadRequestHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {WrongRequestException.class})
  protected ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
    WrongRequestException exception = null;
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json;UTF-8");
    try {
      exception = (WrongRequestException) ex;
    } catch (ClassCastException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(), exception.getErrors()), headers, HttpStatus.BAD_REQUEST);
  }

}
