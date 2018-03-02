package com.exchange.rates.mapper;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyDescription;
import com.exchange.rates.soapclient.SOAPConnector;

@Service
public class CurrencyMapperImpl implements CurrencyMapper {

  @Autowired
  private SOAPConnector soapConnector;

  @Override
  public List<Currency> getAllCurrencies() {
    return sampleCurrencies();
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
