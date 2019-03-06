package com.exchange.rates.soapclient.test;

import static org.junit.Assert.*;

import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.SOAPClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SOAPClientConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SOAPClientTest {

    private static final Logger logger = LoggerFactory.getLogger(SOAPClientTest.class);

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    @Qualifier("soapConnectionFactory")
    private SOAPConnectionFactory soapConnectionFactory;

    @Autowired
    @Qualifier("soapMessageFactory")
    private MessageFactory messageFactory;

    @Test
    public void checkSoapConnection() throws SOAPException {
        String dateString = "2014-06-06";
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        SOAPMessage request = constructSoapMessage(dateString);
        logger.info("{}", request);
        request.getMimeHeaders().removeHeader("Content-Length");
        request.getMimeHeaders().removeHeader("Content-Type");

/*        final StringWriter sw = new StringWriter();

        try {
            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(request.getSOAPPart()),
                    new StreamResult(sw));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}", sw);*/
        request.getMimeHeaders().removeHeader("Accept");
        SOAPMessage soapResponse = soapConnection.call(request, SOAPClientConfig.WEBSERVICE_URL);
        soapResponse.getSOAPBody();
        assertNotNull(soapResponse);
    }

    private SOAPMessage constructSoapMessage(String dateString) throws SOAPException {
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        soapMessage.getSOAPPart().getEnvelope().setPrefix("soapenv");
        soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("env");
        soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("exc", SOAPClientConfig.NAMESPACE);
        soapMessage.getSOAPHeader().setPrefix("soapenv");
        soapMessage.getSOAPBody().setPrefix("soapenv");
        SOAPElement soapBodyElement0 = soapMessage.getSOAPBody().addChildElement("getExchangeRatesByDate", "exc");
        SOAPElement soapBodyElement1 = soapBodyElement0.addChildElement("Date", "exc");
        soapBodyElement1.addTextNode(dateString);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", SOAPClientConfig.NAMESPACE + "getExchangeRatesByDate");
        headers.addHeader("Content-Type" , "text/xml; charset=utf-8");
        //soapMessage.saveChanges();
        return soapMessage;
    }

}
