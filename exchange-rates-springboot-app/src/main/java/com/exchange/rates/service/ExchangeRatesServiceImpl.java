package com.exchange.rates.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;
import com.exchange.rates.mapper.CurrencyMapper;
import com.exchange.rates.mapper.CurrencyRateMapper;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

  @Autowired
  private CurrencyMapper currencyMapper;
  
  @Autowired
  private CurrencyRateMapper rateMapper;
  
  @Override
  public List<Currency> getAllCurrencies() {
    return currencyMapper.getAllCurrencies();
  }

  @Override
  public List<CurrencyRate> getCurrencyRatesForDate(Date date) {
    return rateMapper.getCurrencyRatesForDate(date);
  }

  @Override
  public List<CurrencyRate> getRateChangesForDateComparedWithYesterday(Date date) {
    // TODO Auto-generated method stub
    return null;
  }

}
