package com.exchange.rates.controller.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;
import com.exchange.rates.service.ExchangeRatesService;

@Controller
@RequestMapping(value = "/currency", produces = APPLICATION_JSON_UTF8_VALUE)
public class CurrencyController {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
  
  @Autowired
  private ExchangeRatesService exchangeRates;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public @ResponseBody List<Currency> getAllCurrencies() {
    return exchangeRates.getAllCurrencies();
  }

  @RequestMapping(value = "/rate/date/{formattedDate}", method = RequestMethod.GET)
  public @ResponseBody List<CurrencyRate> getAllRatesForDate(@PathVariable("formattedDate") String formattedDate) {
    Date date = new Date();
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }  
    return exchangeRates.getCurrencyRatesForDate(date);
  }
  
  @RequestMapping(value = "/rate/change/date/{formattedDate}", method = RequestMethod.GET)
  public @ResponseBody List<CurrencyRate> getAllRateChangesForDateComparedWithYesterday(@PathVariable("formattedDate") String formattedDate) {
    Date date = new Date();
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }  
    return exchangeRates.getCurrencyRatesForDate(date);
  }
  
}