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
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;
import com.exchange.rates.utils.CurrencyRateComparator;

@Service
public class CurrencyRateMapperImpl implements CurrencyRateMapper {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyRateMapperImpl.class);
  
  @Override
  public List<CurrencyRate> getCurrencyRatesForDate(Date date) {
    return generateListBefore();
  }

  @Override
  public List<CurrencyRate> getRateChangesForDateComparedWithYesterday(Date date) {
    List<CurrencyRate> before = generateListBefore();
    List<CurrencyRate> after = generateListAfter();

    for (int i = 0; i < after.size(); i++) {      // in case we have some sloppy third-party webservice which returns different count of currencies 
      for (int j = 0; j < before.size(); j++) {   // and different currency order for each call - let's have 2 "for" loops to iterate through each currency 
        if (after.get(i).getCurrency().getCode().equalsIgnoreCase(before.get(j).getCurrency().getCode())) { 
          after.get(i).calculateDifference(before.get(j));
        }
      }
    }

    Collections.sort(after, CurrencyRateComparator.BY_ABS_DIFF_ASC);
    return after;
  }

  private List<CurrencyRate> generateListBefore() {
    List<CurrencyRate> list = new LinkedList<CurrencyRate>();
    Date april2013 = new Date();
    String formattedApril = "2013-04-01";
    try {
      april2013 = new SimpleDateFormat("yyyy-MM-dd").parse(formattedApril);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }

    CurrencyRate usdApril = new CurrencyRate();
    usdApril.setActualDate(april2013);
    usdApril.setCurrency(new Currency().setCode("USD"));
    usdApril.setQuantity(new Double("1"));
    usdApril.setRate(new Double("2.6984"));
    usdApril.setUnit("LTL per 1 currency unit");
    
    CurrencyRate eurApril = new CurrencyRate();
    eurApril.setActualDate(april2013);
    eurApril.setCurrency(new Currency().setCode("EUR"));
    eurApril.setQuantity(new Double("1"));
    eurApril.setRate(new Double("3.4528"));
    eurApril.setUnit("LTL per 1 currency unit");
    
    CurrencyRate plnApril = new CurrencyRate();
    plnApril.setActualDate(april2013);
    plnApril.setCurrency(new Currency().setCode("PLN"));
    plnApril.setQuantity(new Double("10"));
    plnApril.setRate(new Double("8.2451"));
    plnApril.setUnit("LTL per 10 currency units");
    
    list.add(eurApril);
    list.add(usdApril);
    list.add(plnApril);
    
    return list;
  }
  
  private List<CurrencyRate> generateListAfter() {
    List<CurrencyRate> list = new LinkedList<CurrencyRate>();
    
    Date may2013 = new Date();
    String formattedMay = "2013-05-01";
    try {
      may2013 = new SimpleDateFormat("yyyy-MM-dd").parse(formattedMay);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }
    
    CurrencyRate usdMay = new CurrencyRate();
    usdMay.setActualDate(may2013);
    usdMay.setCurrency(new Currency().setCode("USD"));
    usdMay.setQuantity(new Double("1"));
    usdMay.setRate(new Double("2.6433"));
    usdMay.setUnit("LTL per 1 currency unit");
    
    CurrencyRate eurMay = new CurrencyRate();
    eurMay.setActualDate(may2013);
    eurMay.setCurrency(new Currency().setCode("EUR"));
    eurMay.setQuantity(new Double("1"));
    eurMay.setRate(new Double("3.4528"));
    eurMay.setUnit("LTL per 1 currency unit");
    
    CurrencyRate plnMay1 = new CurrencyRate();
    plnMay1.setActualDate(may2013);
    plnMay1.setCurrency(new Currency().setCode("PLN"));
    plnMay1.setQuantity(new Double("1"));
    plnMay1.setRate(new Double("0.83167"));
    plnMay1.setUnit("LTL per 1 currency units");
    
    CurrencyRate plnMay2 = new CurrencyRate();
    plnMay2.setActualDate(may2013);
    plnMay2.setCurrency(new Currency().setCode("PLN"));
    plnMay2.setQuantity(new Double("10"));
    plnMay2.setRate(new Double("8.3167"));
    plnMay2.setUnit("LTL per 10 currency units");
    
    list.add(usdMay);
    list.add(eurMay);
    list.add(plnMay2);
    
    return list;
  }
}
