package com.exchange.rates.mapper;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyDescription;
import com.exchange.rates.errorhandling.ErrorField;
import com.exchange.rates.errorhandling.WrongRequestException;
import com.exchange.rates.soapclient.SOAPConnector;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import lt.lb.webservices.exchangerates.GetExchangeRatesByDateResponse;
import lt.lb.webservices.exchangerates.GetListOfCurrencies;
import lt.lb.webservices.exchangerates.GetListOfCurrenciesResponse;

@Service
public class CurrencyMapperImpl implements CurrencyMapper {

  @Autowired
  private SOAPConnector soapConnector;

  @Override
  public List<Currency> getAllCurrencies() {
    GetListOfCurrencies request = new GetListOfCurrencies();
    GetListOfCurrenciesResponse actualResponse = null;
    try {
      actualResponse = (GetListOfCurrenciesResponse) soapConnector.callWebService(request);
    } catch (Exception e) { // in case external webservice is faulty
      throw new WrongRequestException(e.getMessage(), new LinkedList<ErrorField>());
    }
    //return convertExternalWebserviceResponseToCurrenciesLightweight(actualResponse);
    return convertExternalWebserviceResponseToCurrencies(actualResponse);
  }
  
  private List<Currency> convertExternalWebserviceResponseToCurrenciesLightweight(GetListOfCurrenciesResponse actualResponse) {
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
    return currencyList;
  }
  
  private List<Currency> convertExternalWebserviceResponseToCurrencies(GetListOfCurrenciesResponse actualResponse) {
    List<Object> content = actualResponse.getGetListOfCurrenciesResult().getContent();
    Object firstElement = content.get(0);
    ElementImpl element = (ElementImpl) firstElement;
    NodeList currenciesNodeList = element.getChildNodes();
    
    List<Currency> currencyList = new LinkedList<Currency>();
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
    return currencyList;
  }
  
  private List<Currency> sampleCurrencies() {
    List<Currency> currencyList = new LinkedList<Currency>();
    
    Currency usd = new Currency();
    usd.setCode("USD"); 
    CurrencyDescription usdDescEN = new CurrencyDescription();
    usdDescEN.setLanguage("EN"); usdDescEN.setDescription("U.S. Dollar");
    CurrencyDescription usdDescLT = new CurrencyDescription();
    usdDescLT.setLanguage("LT"); usdDescLT.setDescription("JAV doleris");
    usd.getDescriptions().add(usdDescEN); usd.getDescriptions().add(usdDescLT);

    Currency eur = new Currency();
    eur.setCode("EUR");
    CurrencyDescription eurDescEN = new CurrencyDescription();
    eurDescEN.setLanguage("EN"); eurDescEN.setDescription("Euro");
    CurrencyDescription eurDescLT = new CurrencyDescription();
    eurDescLT.setLanguage("LT"); eurDescLT.setDescription("Euras");
    eur.getDescriptions().add(eurDescEN); eur.getDescriptions().add(eurDescLT);

    currencyList.add(usd); currencyList.add(eur);
    return currencyList;
  }
  
}
