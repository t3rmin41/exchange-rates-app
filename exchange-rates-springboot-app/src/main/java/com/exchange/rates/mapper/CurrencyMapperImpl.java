package com.exchange.rates.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exchange.rates.soapclient.SOAPConnector;

@Service
public class CurrencyMapperImpl implements CurrencyMapper {

  @Autowired
  private SOAPConnector soapConnector;
  
}
