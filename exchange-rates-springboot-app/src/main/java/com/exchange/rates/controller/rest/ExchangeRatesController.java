package com.exchange.rates.controller.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.service.ExchangeRatesService;

@Controller
@RequestMapping(value = "/currencies", produces = APPLICATION_JSON_UTF8_VALUE)
public class ExchangeRatesController {

  @Autowired
  private ExchangeRatesService exchangeRates;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<Currency> getAllCurrencies() {
    return exchangeRates.getAllCurrencies();
  }

}
