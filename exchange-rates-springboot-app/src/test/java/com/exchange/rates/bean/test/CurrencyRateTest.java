package com.exchange.rates.bean.test;

import static org.junit.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;
import com.exchange.rates.utils.CurrencyRateComparator;

public class CurrencyRateTest {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyRateTest.class);
  
  @Test
  public void checkCurrencyRateComparison() {
    List<CurrencyRate> before = new LinkedList<CurrencyRate>();
    List<CurrencyRate> after = new LinkedList<CurrencyRate>();
    List<CurrencyRate> resultList = new LinkedList<CurrencyRate>();
    
    Date april2013 = new Date();
    Date may2013 = new Date();
    String formattedApril = "2013-04-01";
    String formattedMay = "2013-05-01";
    try {
      april2013 = new SimpleDateFormat("yyyy-MM-dd").parse(formattedApril);
      may2013 = new SimpleDateFormat("yyyy-MM-dd").parse(formattedMay);
    } catch (ParseException e) {
      logger.error("{}", e.getCause());
    }
    
    CurrencyRate usdApril = new CurrencyRate();
    usdApril.setActualDate(april2013);
    usdApril.setComparedDate(may2013);
    usdApril.setCurrency(new Currency().setCode("USD"));
    usdApril.setQuantity(new Double("1"));
    usdApril.setRate(new Double("2.6984"));
    usdApril.setUnit("LTL per 1 currency unit");

    CurrencyRate usdMay = new CurrencyRate();
    usdMay.setActualDate(may2013);
    usdMay.setComparedDate(april2013);
    usdMay.setCurrency(new Currency().setCode("USD"));
    usdMay.setQuantity(new Double("1"));
    usdMay.setRate(new Double("2.6433"));
    usdMay.setUnit("LTL per 1 currency unit");
    
    CurrencyRate eurApril = new CurrencyRate();
    eurApril.setActualDate(april2013);
    eurApril.setComparedDate(may2013);
    eurApril.setCurrency(new Currency().setCode("EUR"));
    eurApril.setQuantity(new Double("1"));
    eurApril.setRate(new Double("3.4528"));
    eurApril.setUnit("LTL per 1 currency unit");

    CurrencyRate eurMay = new CurrencyRate();
    eurMay.setActualDate(may2013);
    eurMay.setComparedDate(may2013);
    eurMay.setCurrency(new Currency().setCode("EUR"));
    eurMay.setQuantity(new Double("1"));
    eurMay.setRate(new Double("3.4528"));
    eurMay.setUnit("LTL per 1 currency unit");
    
    before.add(eurApril); before.add(usdApril);
    after.add(eurMay); after.add(usdMay);

    for (int i = 0; i < after.size(); i++) {      // in case we have some sloppy third-party webservice which returns different count of currencies 
      for (int j = 0; j < before.size(); j++) {   // and different currency order for each call - let's have 2 "for" loops to iterate through each currency 
        if (after.get(i).equals(before.get(j))) { // currency code after.get(i) equals currency code before.get(j) 
          after.get(i).calculateDifference(before.get(j).getRate(), before.get(j).getQuantity());
          resultList.add(after.get(i));
        }
      }
    }
    Collections.sort(resultList, CurrencyRateComparator.BY_DIFF_ASC);
    assertEquals("EUR", resultList.get(0).getCurrency().getCode());
    
    Collections.sort(resultList, CurrencyRateComparator.BY_DIFF_DESC);
    assertEquals("USD", resultList.get(0).getCurrency().getCode());
  }
  
}
