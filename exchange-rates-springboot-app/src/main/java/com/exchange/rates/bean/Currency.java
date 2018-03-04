package com.exchange.rates.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

  private String code;
  private List<CurrencyDescription> descriptions = new LinkedList<CurrencyDescription>();

  public String getCode() {
    return code;
  }
  public Currency setCode(String currencyCode) {
    this.code = currencyCode;
    return this;
  }
  public List<CurrencyDescription> getDescriptions() {
    return descriptions;
  }
  
  @Override
  public String toString() {
    String objectInfo = "";
    objectInfo = "[Currency = (code = " + code + ", descriptions = {";
    while(descriptions.iterator().hasNext()) {
      objectInfo += "(lang="+ descriptions.iterator().next().getLanguage() +"|desc=" + descriptions.iterator().next().getDescription()+")";
    }
    objectInfo += "}]";
    return objectInfo;
  }
  
}
