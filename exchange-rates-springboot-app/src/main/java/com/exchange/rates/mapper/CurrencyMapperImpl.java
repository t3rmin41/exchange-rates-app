package com.exchange.rates.mapper;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exchange.rates.bean.Currency;
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
    usd.setDescriptionEN("U.S. Dollar");
    usd.setDescriptionLT("JAV doleris");
    
    Currency eur = new Currency();
    eur.setCode("EUR");
    eur.setDescriptionEN("Euro");
    eur.setDescriptionLT("Euras");
    
    currencyList.add(usd); currencyList.add(eur);
    return currencyList;
  }
  
}
