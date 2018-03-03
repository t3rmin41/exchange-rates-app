package com.exchange.rates.mapper;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.CurrencyRate;

@Service
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

  @Override
  public List<CurrencyRate> getCurrencyRatesForDate(Date date) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<CurrencyRate> getRateChangesForDateComparedWithYesterday(Date date) {
    // TODO Auto-generated method stub
    return null;
  }

}
