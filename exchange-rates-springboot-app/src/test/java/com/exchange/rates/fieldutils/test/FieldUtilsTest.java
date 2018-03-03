package com.exchange.rates.fieldutils.test;

import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.exchange.rates.bean.Currency;
import com.exchange.rates.bean.CurrencyDescription;
import com.exchange.rates.config.ApplicationConfig;
import com.exchange.rates.config.SOAPConfig;
import com.exchange.rates.soapclient.SOAPConnector;
import lt.lb.webservices.exchangerates.GetListOfCurrencies;
import lt.lb.webservices.exchangerates.GetListOfCurrenciesResponse;
import com.sun.org.apache.xerces.internal.dom.ElementImpl;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ApplicationConfig.class, SOAPConfig.class}, loader=AnnotationConfigContextLoader.class)
public class FieldUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(FieldUtilsTest.class);
  
  @Autowired
  private SOAPConnector soapConnector;
  
  @Ignore
  @Test
  public void testGetAllFieldsList() {

    GetListOfCurrencies request = new GetListOfCurrencies();
    GetListOfCurrenciesResponse actualResponse = (GetListOfCurrenciesResponse) soapConnector.callWebService(request);
    List<Object> content = actualResponse.getGetListOfCurrenciesResult().getContent();
    CurrencyDescription description = new CurrencyDescription("en", "U.S. dollar");
    try {
      Field field = actualResponse.getGetListOfCurrenciesResult().getClass().getDeclaredField("content");
      field.setAccessible(true);
      Object value = field.get(actualResponse.getGetListOfCurrenciesResult());
      logger.info("{}", value);
      Field privateField = description.getClass().getDeclaredField("language");
      privateField.setAccessible(true);
      Object privateValue = privateField.get(description);
      String privateString = (String) privateValue;
      logger.info("{}", privateString);
    } catch (NoSuchFieldException | IllegalAccessException | SecurityException e) {
      e.printStackTrace();
      //logger.error("{}", e.printStackTrace());
    }
    
    Object firstElement = content.get(0);
    ElementImpl element = (ElementImpl) firstElement;
    NodeList nodeList = element.getChildNodes();
    NamedNodeMap namedNodeMap = element.getAttributes();
    Node node = nodeList.item(0);
    int nodeListLength = nodeList.getLength();
    String currencyCode = node.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
    
    
    // Get all fields in this class and all of its parents
    final List<Field> allFields = FieldUtils.getAllFieldsList(element.getClass());

    // Get the fields form each individual class in the type's hierarchy
    final List<Field> allFieldsClass = Arrays.asList(element.getClass().getFields());
    final List<Field> allFieldsParent = Arrays.asList(element.getClass().getFields());
    final List<Field> allFieldsParentsParent = Arrays.asList(element.getClass().getFields());
    final List<Field> allFieldsParentsParentsParent = Arrays.asList(firstElement.getClass().getFields());

    // Test that `getAllFieldsList` did truly get all of the fields of the the class and all its parents
    Assert.assertTrue(allFields.containsAll(allFieldsClass));
    //Assert.assertTrue(allFields.containsAll(allFieldsParent));
    //Assert.assertTrue(allFields.containsAll(allFieldsParentsParent));
    //Assert.assertTrue(allFields.containsAll(allFieldsParentsParentsParent));
  }
  
}
