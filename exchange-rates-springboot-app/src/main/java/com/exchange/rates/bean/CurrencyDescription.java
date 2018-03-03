package com.exchange.rates.bean;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDescription implements Serializable {

  private String language;
  private String description;

  public CurrencyDescription() {
    this.language = "";
    this.description = "";
  }
  
  public CurrencyDescription(String lang, String desc) {
    this.language = lang;
    this.description = desc;
  }
  
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

}
