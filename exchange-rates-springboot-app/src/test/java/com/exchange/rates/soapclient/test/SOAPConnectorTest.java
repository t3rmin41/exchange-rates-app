package com.exchange.rates.soapclient.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.SOAPConfig;
import com.exchange.rates.soapclient.SOAPConnector;
import lt.lb.webservices.exchangerates.GetExchangeRate;
import lt.lb.webservices.exchangerates.GetExchangeRateResponse;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDate;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDateResponse;
import lt.lb.webservices.exchangerates.GetListOfCurrencies;
import lt.lb.webservices.exchangerates.GetListOfCurrenciesResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ApplicationConfig.class, SOAPConfig.class}, loader=AnnotationConfigContextLoader.class)
public class SOAPConnectorTest {

  private static final Logger logger = LoggerFactory.getLogger(SOAPConnectorTest.class);
  
  @Autowired
  private SOAPConnector soapConnector;

  @Test
  public void checkExchangeRatesByDate() {
    GetExchangeRatesByDate request = new GetExchangeRatesByDate();
    request.setDate("2013-04-11");
    GetExchangeRatesByDateResponse expectedResponse = new GetExchangeRatesByDateResponse();
    GetExchangeRatesByDateResponse actualResponse = (GetExchangeRatesByDateResponse) soapConnector.callWebService(request);
    logger.info("{}", actualResponse.getGetExchangeRatesByDateResult().getContent());
    assertEquals(expectedResponse.getClass(), actualResponse.getClass());
  }
  /**/
  
}
