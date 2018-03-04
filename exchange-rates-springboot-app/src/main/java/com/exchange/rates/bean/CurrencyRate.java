package com.exchange.rates.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate implements Comparable<CurrencyRate>, Serializable {

  private static final int precision = 5;
  
  private Date actualDate = new Date();
  private Date comparedDate = new Date();
  private Currency currency;
  private Double quantity;
  private Double rate = new Double(0);
  private String unit;
  private Double difference = new Double(0);
  private String formattedDifference;

  public Date getActualDate() {
    return actualDate;
  }
  public void setActualDate(Date actualDate) {
    this.actualDate = actualDate;
  }
  public Date getComparedDate() {
    return comparedDate;
  }
  public void setComparedDate(Date comparedDate) {
    this.comparedDate = comparedDate;
  }
  public Currency getCurrency() {
    return currency;
  }
  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
  public Double getQuantity() {
    return quantity;
  }
  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }
  public Double getRate() {
    return rate;
  }
  public void setRate(Double rate) {
    this.rate = rate;
  }
  public String getUnit() {
    return unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }
  public Double getDifference() {
    return this.difference;
  }
  public void setDifference(Double change) {
    this.difference = change;
  }
  public String getFormattedDifference() {
    this.formattedDifference = String.format("%."+precision+"g%n", difference);
    return formattedDifference;
  }
  public void setFormattedDifference(String formattedDifference) {
    this.formattedDifference = formattedDifference;
  }
  
  public void calculateDifference(CurrencyRate anotherRate) {
    this.comparedDate = anotherRate.getActualDate();
    // negative difference means the rate has dropped, positive means the rate has raised relative to compared date rate
    this.difference = this.rate*this.quantity - anotherRate.getQuantity()*anotherRate.getRate();
    //if (this.comparedDate.getTime() > this.actualDate.getTime()) {
    //  this.difference = this.rate*this.quantity - anotherRate.getQuantity()*anotherRate.getRate();
    //} else {
    //  this.difference = anotherRate.getQuantity()*anotherRate.getRate() - this.rate*this.quantity;
    //}
  }

  public Double getAbsoluteDifference() {
    return Math.abs(this.difference);
  }
  
  @Override
  public int compareTo(CurrencyRate comparedRate) {
    if (this.difference > comparedRate.getDifference()) return 1;
    if (this.difference < comparedRate.getDifference()) return -1;
    return 0;
  }

  @Override
  public String toString() {
    String objectInfo = "";
    String actualDateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(actualDate);
    String compareDateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(comparedDate);
    objectInfo = "[CurrencyRate = (code = "+currency.getCode()+", quantity = "+quantity+", rate = "+rate+", difference = "+getFormattedDifference()+
                 ", actualDate = "+actualDateFormatted+", comparedDate = "+compareDateFormatted+")]";
    return objectInfo;
  }

}
