package com.exchange.rates.mapper;

import java.util.Date;
import java.util.List;
import com.exchange.rates.bean.CurrencyRate;

public interface CurrencyRateMapper {

  List<CurrencyRate> getCurrencyRatesForDate(Date date);
  
  List<CurrencyRate> getRateChangesForDateComparedWithYesterday(Date date);
}
