package com.exchange.rates.service;

import java.util.List;
import com.exchange.rates.bean.Currency;

public interface ExchangeRatesService {

  List<Currency> getAllCurrencies();
  
}
