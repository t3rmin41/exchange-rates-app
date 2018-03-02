package com.exchange.rates.soapclient.test;

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.SOAPConfig;
import com.exchange.rates.soapclient.SOAPConnector;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;

import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyDescription;
import lt.lb.webservices.exchangerates.GetExchangeRate;
import lt.lb.webservices.exchangerates.GetExchangeRateResponse;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDate;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDateResponse;
import lt.lb.webservices.exchangerates.GetListOfCurrencies;
import lt.lb.webservices.exchangerates.GetListOfCurrenciesResponse;
import lt.lb.webservices.exchangerates.GetListOfCurrenciesResponse.GetListOfCurrenciesResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ApplicationConfig.class, SOAPConfig.class}, loader=AnnotationConfigContextLoader.class)
public class SOAPConnectorTest {

  private static final Logger logger = LoggerFactory.getLogger(SOAPConnectorTest.class);
  
  @Autowired
  private SOAPConnector soapConnector;

  @Ignore
  @Test
  public void checkExchangeRatesByDate() {
    GetExchangeRatesByDate request = new GetExchangeRatesByDate();
    request.setDate("2013-04-11");
    GetExchangeRatesByDateResponse expectedResponse = new GetExchangeRatesByDateResponse();
    GetExchangeRatesByDateResponse actualResponse = (GetExchangeRatesByDateResponse) soapConnector.callWebService(request);
    logger.info("{}", actualResponse.getGetExchangeRatesByDateResult().getContent());
    assertEquals(expectedResponse.getClass(), actualResponse.getClass());
  }
  
  @Test
  public void checkListOfCurrenciesRetrievalLightweight() {
    GetListOfCurrencies request = new GetListOfCurrencies();
    GetListOfCurrenciesResponse expectedResponse = new GetListOfCurrenciesResponse();
    GetListOfCurrenciesResponse actualResponse = (GetListOfCurrenciesResponse) soapConnector.callWebService(request);
    
    List<Object> content = actualResponse.getGetListOfCurrenciesResult().getContent();
    Object firstElement = content.get(0);
    ElementImpl element = (ElementImpl) firstElement;
    NodeList currenciesNodeList = element.getChildNodes();

    List<Currency> currencyList = new LinkedList<Currency>();

    for (int i = 0; i < currenciesNodeList.getLength(); i++) {
      Currency currency = new Currency();
      currency.setCode(currenciesNodeList.item(i).getChildNodes().item(0).getChildNodes().item(0).getNodeValue());
      currency.getDescriptions().add(
          new CurrencyDescription(currenciesNodeList.item(i).getChildNodes().item(1).getAttributes().item(0).getNodeValue(),
                                  currenciesNodeList.item(i).getChildNodes().item(1).getChildNodes().item(0).getNodeValue()
                                 ));
      currency.getDescriptions().add(
          new CurrencyDescription(currenciesNodeList.item(i).getChildNodes().item(2).getAttributes().item(0).getNodeValue(),
                                  currenciesNodeList.item(i).getChildNodes().item(2).getChildNodes().item(0).getNodeValue()
                                 ));
      currencyList.add(currency);
    }
    
    for (Currency c : currencyList) {
      logger.info("{}", c);
    }
    
    assertEquals(expectedResponse.getClass(), actualResponse.getClass());
  }
  
  @Ignore
  @Test
  public void checkListOfCurrenciesRetrieval() {
    GetListOfCurrencies request = new GetListOfCurrencies();
    GetListOfCurrenciesResponse response = new GetListOfCurrenciesResponse();
    GetListOfCurrenciesResponse expectedResponse = new GetListOfCurrenciesResponse();
    GetListOfCurrenciesResponse actualResponse = (GetListOfCurrenciesResponse) soapConnector.callWebService(request);
    
    List<Object> content = actualResponse.getGetListOfCurrenciesResult().getContent();
    Object firstElement = content.get(0);
    ElementImpl element = (ElementImpl) firstElement;
    NodeList currenciesNodeList = element.getChildNodes();
    Node firstCurrencyNode = currenciesNodeList.item(0);

    List<Currency> currencyList = new LinkedList<Currency>();

    String currencyCode = firstCurrencyNode.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
    String engDesc = firstCurrencyNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue();

    NamedNodeMap attributes = firstCurrencyNode.getChildNodes().item(1).getAttributes();

    
    for (int i = 0; i < currenciesNodeList.getLength(); i++) {
      Currency currency = new Currency();
      for (int j = 0; j < currenciesNodeList.item(i).getChildNodes().getLength(); j++) {
        if ("currency".equals(currenciesNodeList.item(i).getChildNodes().item(j).getNodeName())) {
          currency.setCode(currenciesNodeList.item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue());
        } else if ("description".equals(currenciesNodeList.item(i).getChildNodes().item(j).getNodeName())) {
          CurrencyDescription description = new CurrencyDescription();
          description.setLanguage(currenciesNodeList.item(i).getChildNodes().item(j).getAttributes().item(0).getNodeValue());
          description.setDescription(currenciesNodeList.item(i).getChildNodes().item(j).getChildNodes().item(0).getNodeValue());
          currency.getDescriptions().add(description);
        }
      }
      currencyList.add(currency);
    }
    
    for (Currency c : currencyList) {
      logger.info("{}", c);
    }
    
    assertEquals(expectedResponse.getClass(), actualResponse.getClass());
  }
  
}
