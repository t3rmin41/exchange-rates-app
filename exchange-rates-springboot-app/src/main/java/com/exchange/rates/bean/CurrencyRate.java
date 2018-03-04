package com.exchange.rates.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate implements Comparable<CurrencyRate>, Serializable {

  private static final int precision = 5;
  public static final int PRECISION = 100000;
  
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
  public CurrencyRate setActualDate(Date actualDate) {
    this.actualDate = actualDate;
    return this;
  }
  public Date getComparedDate() {
    return comparedDate;
  }
  public CurrencyRate setComparedDate(Date comparedDate) {
    this.comparedDate = comparedDate;
    return this;
  }
  public Currency getCurrency() {
    return currency;
  }
  public CurrencyRate setCurrency(Currency currency) {
    this.currency = currency;
    return this;
  }
  public Double getQuantity() {
    return quantity;
  }
  public CurrencyRate setQuantity(Double quantity) {
    this.quantity = quantity;
    return this;
  }
  public Double getRate() {
    return rate;
  }
  public CurrencyRate setRate(Double rate) {
    this.rate = rate;
    return this;
  }
  public String getUnit() {
    return unit;
  }
  public CurrencyRate setUnit(String unit) {
    this.unit = unit;
    return this;
  }
  public Double getDifference() {
    return this.difference;
  }
  public CurrencyRate setDifference(Double change) {
    this.difference = change;
    return this;
  }
  public String getFormattedDifference() {
    this.formattedDifference = String.format("%."+precision+"g%n", difference);
    return formattedDifference;
  }
  public CurrencyRate setFormattedDifference(String formattedDifference) {
    this.formattedDifference = formattedDifference;
    return this;
  }
  
  public CurrencyRate calculateDifference(CurrencyRate anotherCurrencyRate) {
    this.comparedDate = anotherCurrencyRate.getActualDate();
    BigDecimal thisRate = new BigDecimal(this.rate);
    BigDecimal thisQuantity = new BigDecimal(this.quantity);
    BigDecimal anotherRate = new BigDecimal(anotherCurrencyRate.getRate());
    BigDecimal anotherQuantity = new BigDecimal(anotherCurrencyRate.getQuantity());
    MathContext precision = new MathContext(PRECISION);
    // negative difference means the rate has dropped, positive means the rate has raised relative to compared date rate
    this.difference = thisRate.divide(thisQuantity, precision).subtract(anotherRate.divide(anotherQuantity, precision), precision).doubleValue();
    //if (this.comparedDate.getTime() > this.actualDate.getTime()) {
    //  this.difference = this.rate*this.quantity - anotherRate.getQuantity()*anotherRate.getRate();
    //} else {
    //  this.difference = anotherRate.getQuantity()*anotherRate.getRate() - this.rate*this.quantity;
    //}
    return this;
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
