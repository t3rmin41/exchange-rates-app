package com.exchange.rates.soapclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SOAPConnector {

  @Autowired
  private WebServiceTemplate webServiceTemplate;
  
  public Object callWebService(Object request){
    return webServiceTemplate.marshalSendAndReceive(request);
  }
}
