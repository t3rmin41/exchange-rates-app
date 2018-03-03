package com.exchange.rates.bean;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate implements Comparable<CurrencyRate>, Serializable {

  private Date actualDate;
  private Date comparedDate;
  private String code;
  private Double quantity;
  private Double rate;
  private String unit;
  private Double change;

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
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
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
  public Double getChange() {
    return change;
  }
  public void setChange(Double change) {
    this.change = change;
  }

  @Override
  public int compareTo(CurrencyRate o) {
    // TODO Auto-generated method stub
    return 0;
  }

}
