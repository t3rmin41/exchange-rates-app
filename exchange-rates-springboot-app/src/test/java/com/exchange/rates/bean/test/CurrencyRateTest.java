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
    List<CurrencyRate> before = generateListBefore();
    List<CurrencyRate> after = generateListAfter();

    for (int i = 0; i < after.size(); i++) {      // in case we have some sloppy third-party webservice which returns different count of currencies 
      for (int j = 0; j < before.size(); j++) {   // and different currency order for each call - let's have 2 "for" loops to iterate through each currency 
        if (after.get(i).getCurrency().getCode().equalsIgnoreCase(before.get(j).getCurrency().getCode())) { 
          after.get(i).calculateDifference(before.get(j));
        }
      }
    }

    Collections.sort(after, CurrencyRateComparator.BY_DIFF_ASC);
    assertEquals("PLN", after.get(0).getCurrency().getCode());

    Collections.sort(after, CurrencyRateComparator.BY_DIFF_DESC);
    assertEquals("USD", after.get(0).getCurrency().getCode());

    Collections.sort(after, CurrencyRateComparator.BY_ABS_DIFF_ASC);
    assertEquals("USD", after.get(0).getCurrency().getCode());

    Collections.sort(after, CurrencyRateComparator.BY_ABS_DIFF_DESC);
    assertEquals("EUR", after.get(0).getCurrency().getCode());
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

    CurrencyRate usdApril = new CurrencyRate(april2013);
    usdApril.setCurrency(new Currency().setCode("USD"));
    usdApril.setQuantity(new Float("1"));
    usdApril.setRate(new Float("2.6984"));
    usdApril.setUnit("LTL per 1 currency unit");
    
    CurrencyRate eurApril = new CurrencyRate(april2013);
    eurApril.setCurrency(new Currency().setCode("EUR"));
    eurApril.setQuantity(new Float("1"));
    eurApril.setRate(new Float("3.4528"));
    eurApril.setUnit("LTL per 1 currency unit");
    
    CurrencyRate plnApril = new CurrencyRate(april2013);
    plnApril.setCurrency(new Currency().setCode("PLN"));
    plnApril.setQuantity(new Float("10"));
    plnApril.setRate(new Float("8.2451"));
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
    
    CurrencyRate usdMay = new CurrencyRate(may2013);
    usdMay.setCurrency(new Currency().setCode("USD"));
    usdMay.setQuantity(new Float("1"));
    usdMay.setRate(new Float("2.6433"));
    usdMay.setUnit("LTL per 1 currency unit");
    
    CurrencyRate eurMay = new CurrencyRate(may2013);
    eurMay.setCurrency(new Currency().setCode("EUR"));
    eurMay.setQuantity(new Float("1"));
    eurMay.setRate(new Float("3.4528"));
    eurMay.setUnit("LTL per 1 currency unit");
    
    CurrencyRate plnMay1 = new CurrencyRate(may2013);
    plnMay1.setCurrency(new Currency().setCode("PLN"));
    plnMay1.setQuantity(new Float("1"));
    plnMay1.setRate(new Float("0.83167"));
    plnMay1.setUnit("LTL per 1 currency units");
    
    CurrencyRate plnMay2 = new CurrencyRate(may2013);
    plnMay2.setCurrency(new Currency().setCode("PLN"));
    plnMay2.setQuantity(new Float("10"));
    plnMay2.setRate(new Float("8.3167"));
    plnMay2.setUnit("LTL per 10 currency units");
    
    list.add(usdMay);
    list.add(eurMay);
    list.add(plnMay2);
    
    return list;
  }
  
}
