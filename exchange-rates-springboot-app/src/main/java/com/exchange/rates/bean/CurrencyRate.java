package com.exchange.rates.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate implements Comparable<CurrencyRate>, Serializable {

  public static final long PRECISION = 1_000_000_000;
  
  private static final int DIGITS_AFTER_DOT = 5;
  private static final String TZONE = "Europe/Vilnius";
  private static final String TSTAMP_FORMAT = "yyyy-MM-dd";

  private Date actualDate;
  private Date comparedDate;
  private Currency currency;
  private float quantity;
  private float rate;
  private String unit;
  private float difference;
  private String formattedDifference;

  public CurrencyRate(Date date) {
    this.actualDate = date;
    this.comparedDate = date;
    this.rate = 0f;
    this.difference = 0f;
    this.quantity = 1f;
  }
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TSTAMP_FORMAT, timezone=TZONE)
  public Date getActualDate() {
    return actualDate;
  }
  public CurrencyRate setActualDate(Date actualDate) {
    this.actualDate = actualDate;
    return this;
  }
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TSTAMP_FORMAT, timezone=TZONE)
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
  public float getQuantity() {
    return quantity;
  }
  public CurrencyRate setQuantity(float quantity) {
    this.quantity = quantity;
    return this;
  }
  public float getRate() {
    return rate;
  }
  public CurrencyRate setRate(float rate) {
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
  public float getDifference() {
    return this.difference;
  }
  public CurrencyRate setDifference(float change) {
    this.difference = change;
    return this;
  }
  public String getFormattedDifference() {
    this.formattedDifference = String.format("%."+DIGITS_AFTER_DOT+"f", difference);
    return formattedDifference;
  }
  public CurrencyRate setFormattedDifference(String formattedDifference) {
    this.formattedDifference = formattedDifference;
    return this;
  }
  
  public float getAbsoluteDifference() {
    return Math.abs(this.difference);
  }
  
  public String getAbsoluteDifferenceFormatted() {
    return String.format("%."+DIGITS_AFTER_DOT+"f", Math.abs(this.difference));
  }
  
  public CurrencyRate calculateDifference(CurrencyRate anotherCurrencyRate) {
    this.comparedDate = anotherCurrencyRate.getActualDate();
    this.difference = this.rate/this.quantity - anotherCurrencyRate.getRate()/anotherCurrencyRate.getQuantity();
    return this;
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
