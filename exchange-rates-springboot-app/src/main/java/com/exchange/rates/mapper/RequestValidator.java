package com.exchange.rates.mapper;

import java.io.Serializable;
import java.util.List;
import com.exchange.rates.errorhandling.ErrorField;
import com.exchange.rates.errorhandling.WrongRequestException;

public interface RequestValidator {

  List<ErrorField> validateRequest(Serializable requestData) throws WrongRequestException;
}
