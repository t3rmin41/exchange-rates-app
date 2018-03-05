package com.exchange.rates.errorhandling;

import java.util.LinkedList;
import java.util.List;

public class ErrorResponse {

  private String error;
  private final List<ErrorField> errors = new LinkedList<ErrorField>();
  
  public ErrorResponse(String message, List<ErrorField> errors) {
    this.error = message;
    this.errors.addAll(errors);
  }
  
  public String getError() {
    return error;
  }
  public ErrorResponse setError(String errorMessage) {
    this.error = errorMessage;
    return this;
  }
  public List<ErrorField> getErrors() {
    return errors;
  }

}

