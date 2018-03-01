package com.exchange.rates.bean;

public class Currency {

  private String currencyCode;
  private String descriptionLT;
  private String descriptionEN;

  public String getCurrencyCode() {
    return currencyCode;
  }
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
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
