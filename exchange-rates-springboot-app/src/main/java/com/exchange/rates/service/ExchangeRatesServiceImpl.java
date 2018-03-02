package com.exchange.rates.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.mapper.CurrencyMapper;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

  @Autowired
  private CurrencyMapper currencyMapper;
  
  @Override
  public List<Currency> getAllCurrencies() {
    return currencyMapper.getAllCurrencies();
  }

}
