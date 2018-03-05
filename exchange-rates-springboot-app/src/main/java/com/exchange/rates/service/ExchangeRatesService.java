package com.exchange.rates.service;

import java.util.Date;
import java.util.List;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;

public interface ExchangeRatesService {

  List<Currency> getAllCurrencies();
  
  List<CurrencyRate> getCurrencyRatesForDate(Date date);
  
  List<CurrencyRate> getRateChangesForDateComparedWithPreviousDay(Date date);
  
  List<CurrencyRate> getRateChangesForDates(Date dateFrom, Date dateTo);
}
