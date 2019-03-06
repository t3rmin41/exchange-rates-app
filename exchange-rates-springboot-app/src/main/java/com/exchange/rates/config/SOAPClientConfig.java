package com.exchange.rates.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

@Configuration
public class SOAPClientConfig {

    private static Logger logger = LoggerFactory.getLogger(SOAPClientConfig.class);

    public static final String WEBSERVICE_URL = "http://old.lb.lt/webservices/ExchangeRates/ExchangeRates.asmx?WSDL";
    public static final String NAMESPACE = "http://webservices.lb.lt/ExchangeRates";

    @Bean(name = "soapMessageFactory")
    public MessageFactory messageFactory() throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        return messageFactory;
    }

    @Bean(name = "soapConnectionFactory")
    public SOAPConnectionFactory soapConnectionFactory() throws SOAPException {
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        return connectionFactory;
    }

}
