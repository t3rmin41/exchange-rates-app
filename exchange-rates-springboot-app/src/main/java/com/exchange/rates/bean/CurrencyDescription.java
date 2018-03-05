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
  public CurrencyDescription setLanguage(String language) {
    this.language = language;
    return this;
  }
  public String getDescription() {
    return description;
  }
  public CurrencyDescription setDescription(String description) {
    this.description = description;
    return this;
  }

}
