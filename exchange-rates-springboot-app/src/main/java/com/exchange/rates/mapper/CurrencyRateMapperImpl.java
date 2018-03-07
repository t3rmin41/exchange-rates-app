package com.exchange.rates.mapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyRate;
import com.exchange.rates.errorhandling.ErrorField;
import com.exchange.rates.errorhandling.WrongRequestException;
import com.exchange.rates.soapclient.SOAPConnector;
import com.exchange.rates.utils.CurrencyRateComparator;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDate;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDateResponse;

@Service
public class CurrencyRateMapperImpl implements CurrencyRateMapper, RequestValidator {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyRateMapperImpl.class);
  
  @Autowired
  private SOAPConnector soapConnector;
  
  @Override
  public List<CurrencyRate> getCurrencyRatesForDate(Date date) {
    GetExchangeRatesByDate request = new GetExchangeRatesByDate();
    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
    request.setDate(dateString);
    GetExchangeRatesByDateResponse response = null;
    try {
      response = (GetExchangeRatesByDateResponse) soapConnector.callWebService(request);
    } catch (Exception e) { // in case external webservice is faulty
      throw new WrongRequestException(e.getMessage(), new LinkedList<ErrorField>());
    }
    return convertExternalWebserviceResponseToCurrenyRates(response, date);
  }

  @Override
  public List<CurrencyRate> getRateChangesForDateComparedWithPreviousDay(Date date) {
    validateRequest(date, "", "");

    List<CurrencyRate> before = getCurrencyRatesForDate(getPreviousDay(date));
    List<CurrencyRate> after = getCurrencyRatesForDate(date);

    //List<CurrencyRate> before = generateListBefore();
    //List<CurrencyRate> after = generateListAfter();

    for (int i = 0; i < after.size(); i++) {      // in case we have some sloppy third-party webservice which returns different count of currencies 
      for (int j = 0; j < before.size(); j++) {   // and different currency order for each API call - let's have 2 "for" loops to iterate through each currency 
        if (after.get(i).getCurrency().getCode().equalsIgnoreCase(before.get(j).getCurrency().getCode())) { 
          after.get(i).calculateDifference(before.get(j));
        }
      }
    }
    Collections.sort(after, CurrencyRateComparator.BY_ABS_DIFF_ASC);
    return after;
  }

  @Override
  public List<CurrencyRate> getRateChangesForDates(Date dateFrom, Date dateTo) {
    validateRequest(dateFrom, "", "From");
    validateRequest(dateTo, "", "To");


    List<CurrencyRate> before = getCurrencyRatesForDate(dateFrom);
    List<CurrencyRate> after = getCurrencyRatesForDate(dateTo);
    
    
    //List<CurrencyRate> before = generateListBefore();
    //List<CurrencyRate> after = generateListAfter();

    for (int i = 0; i < after.size(); i++) {      // in case we have some sloppy third-party webservice which returns different count of currencies 
      for (int j = 0; j < before.size(); j++) {   // and different currency order for each API call - let's have 2 "for" loops to iterate through each currency 
        if (after.get(i).getCurrency().getCode().equalsIgnoreCase(before.get(j).getCurrency().getCode())) { 
          after.get(i).calculateDifference(before.get(j));
        }
      }
    }
    Collections.sort(after, CurrencyRateComparator.BY_ABS_DIFF_ASC);
    return after;
  }
  
  
  @Override
  public List<ErrorField> validateRequest(Serializable requestData, String prefix, String suffix) throws WrongRequestException {
    List<ErrorField> errors = new LinkedList<ErrorField>();
    Date requestedDate = (Date) requestData;
    if (requestedDate.after(getLastValidDate())) {
      errors.add(new ErrorField(prefix+"datePicked"+suffix, "Date must be before 2015-01-01"));
    }
    if (requestedDate.before(getFirstValidDate())) {
      errors.add(new ErrorField(prefix+"datePicked"+suffix, "Date must be after or equal to 1993-06-25"));
    }
    if (errors.size() > 0) {
      throw new WrongRequestException("Wrong date requested", errors);
    }
    return errors;
  }

  private List<CurrencyRate> convertExternalWebserviceResponseToCurrenyRates(GetExchangeRatesByDateResponse response, Date date) {
    List<CurrencyRate> rates = new LinkedList<CurrencyRate>();
    
    List<Object> content = response.getGetExchangeRatesByDateResult().getContent();
    Object firstElement = content.get(0);
    ElementImpl element = (ElementImpl) firstElement;
    NodeList ratesNodeList = element.getChildNodes();
    for (int i = 0; i < ratesNodeList.getLength(); i++) {
      CurrencyRate rate = new CurrencyRate(date);
      rate.setCurrency(new Currency().setCode(ratesNodeList.item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue()));
      rate.setQuantity(new Float(ratesNodeList.item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue()));
      rate.setRate(new Float(ratesNodeList.item(i).getChildNodes().item(3).getChildNodes().item(0).getNodeValue()));
      rate.setUnit(ratesNodeList.item(i).getChildNodes().item(4).getChildNodes().item(0).getNodeValue());
      rates.add(rate);
    }
    return rates;
  }
  
  private Date getPreviousDay(Date date) {
    Calendar requestedCalendar = Calendar.getInstance();
    requestedCalendar.setTime(date);
    requestedCalendar.add(Calendar.DAY_OF_YEAR, -1);
    Calendar previousCalendar = new GregorianCalendar(requestedCalendar.get(Calendar.YEAR), 
                                                      requestedCalendar.get(java.util.Calendar.MONTH), 
                                                      requestedCalendar.get(java.util.Calendar.DAY_OF_MONTH), 
                                                      requestedCalendar.get(java.util.Calendar.HOUR_OF_DAY), 
                                                      requestedCalendar.get(java.util.Calendar.MINUTE), 
                                                      requestedCalendar.get(java.util.Calendar.SECOND));
    return previousCalendar.getTime();
  }

  private Date getLastValidDate() {
    Calendar lastValidCalendar = new GregorianCalendar();
    lastValidCalendar.set(Calendar.YEAR, 2014);
    lastValidCalendar.set(Calendar.MONTH, 11);
    lastValidCalendar.set(Calendar.DAY_OF_MONTH, 31);
    lastValidCalendar.set(Calendar.HOUR_OF_DAY, 23);
    lastValidCalendar.set(Calendar.MINUTE, 59);
    lastValidCalendar.set(Calendar.SECOND, 59);
    lastValidCalendar.set(Calendar.MILLISECOND, 999);
    return lastValidCalendar.getTime();
  }
  
  private Date getFirstValidDate() {
    Calendar firstValidCalendar = new GregorianCalendar();
    firstValidCalendar.set(Calendar.YEAR, 1993);
    firstValidCalendar.set(Calendar.MONTH, 5);
    firstValidCalendar.set(Calendar.DAY_OF_MONTH, 25);
    firstValidCalendar.set(Calendar.HOUR_OF_DAY, 0);
    firstValidCalendar.set(Calendar.MINUTE, 0);
    firstValidCalendar.set(Calendar.SECOND, 0);
    firstValidCalendar.set(Calendar.MILLISECOND, 0);
    return firstValidCalendar.getTime();
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
