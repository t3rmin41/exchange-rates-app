package com.exchange.rates.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.CurrencyRate;

@Service
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyRateMapperImpl.class);
  
  @Override
  public List<CurrencyRate> getCurrencyRatesForDate(Date date) {
    return sampleList();
  }

  @Override
  public List<CurrencyRate> getRateChangesForDateComparedWithYesterday(Date date) {
    List<CurrencyRate> sortableList = sampleList();
    Collections.sort(sortableList);
    return sortableList;
  }

  /*
  private List<CurrencyRate> sampleCompare() {
    List<CurrencyRate> sampleList = new LinkedList<CurrencyRate>();
    
    Date april2013 = new Date(2013, 4, 1); 
    Date may2013 = new Date(2013, 4, 1);
    
    CurrencyRate usdApril = new CurrencyRate();
    usdApril.setActualDate(april2013);
    usdApril.setCode("USD");
    usdApril.setQuantity(new Double("1"));
    usdApril.setRate(new Double("2.6984"));
    usdApril.setUnit("LTL per 1 currency unit");

    CurrencyRate usdMay = new CurrencyRate();
    usdMay.setActualDate(may2013);
    usdMay.setCode("USD");
    usdMay.setQuantity(new Double("1"));
    usdMay.setRate(new Double("2.6433"));
    usdMay.setUnit("LTL per 1 currency unit");
  }
  /**/
  
  private List<CurrencyRate> sampleList() {
    List<CurrencyRate> sampleList = new LinkedList<CurrencyRate>();

    Date may2013 = new Date();
    String formattedDate = "2013-05-01";
    try {
      may2013 = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }

    CurrencyRate eurMay = new CurrencyRate();
    eurMay.setActualDate(may2013);
    eurMay.setCode("EUR");
    eurMay.setQuantity(new Double("1"));
    eurMay.setRate(new Double("3.4528"));
    eurMay.setUnit("LTL per 1 currency unit");

    CurrencyRate usdMay = new CurrencyRate();
    usdMay.setActualDate(may2013);
    usdMay.setCode("USD");
    usdMay.setQuantity(new Double("1"));
    usdMay.setRate(new Double("2.6433"));
    usdMay.setUnit("LTL per 1 currency unit");
    
    sampleList.add(eurMay);
    sampleList.add(usdMay);
    
    return sampleList;
  }
  
}
