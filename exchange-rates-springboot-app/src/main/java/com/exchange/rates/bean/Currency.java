package com.exchange.rates.bean;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

  private String code;
  private String descriptionLT;
  private String descriptionEN;

  public String getCode() {
    return code;
  }
  public void setCode(String currencyCode) {
    this.code = currencyCode;
  }
  public String getDescriptionLT() {
    return descriptionLT;
  }
  public void setDescriptionLT(String descriptionLT) {
    this.descriptionLT = descriptionLT;
  }
  public String getDescriptionEN() {
    return descriptionEN;
  }
  public void setDescriptionEN(String descriptionEN) {
    this.descriptionEN = descriptionEN;
  }
  
}
