package com.exchange.rates.config;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.soap.SoapVersion;

@Configuration
public class SOAPConfig {

  private static Logger logger = LoggerFactory.getLogger(SOAPConfig.class);
  
  public static final String SOAP_WEBSERVICE_URL = "http://old.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx?WSDL";

  @Bean
  public WebServiceTemplate webServiceTemplate() {
    SaajSoapMessageFactory messageFactory = null;
    try {
      messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL));
    } catch (SOAPException e) {
      logger.error(e.getMessage());
    }
    messageFactory.afterPropertiesSet();
    WebServiceTemplate template = new WebServiceTemplate(messageFactory);
    template.setDefaultUri(SOAP_WEBSERVICE_URL);
    
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("lt.lb.webservices.exchangerates");
    try {
      marshaller.afterPropertiesSet();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    template.setMarshaller(marshaller);
    template.setUnmarshaller(marshaller);
    
    template.afterPropertiesSet();

    //HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
    //UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("user", "secret");
    //messageSender.setCredentials(credentials);
    //template.setMessageSender(messageSender);
    return template;
  }

}
