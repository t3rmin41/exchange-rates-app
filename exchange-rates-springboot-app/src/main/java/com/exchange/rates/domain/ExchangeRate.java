package com.exchange.rates.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRate implements Serializable {

    @XmlElement(name = "date")
    private String date;
    @XmlElement(name = "currency")
    private String currency;
    @XmlElement(name = "quantity")
    private String quantity;
    @XmlElement(name = "rate")
    private String rate;
    @XmlElement(name = "unit")
    private String unit;

    public String getDate() {
        return date;
    }

    public ExchangeRate setDate(String date) {
        this.date = date;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public ExchangeRate setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public ExchangeRate setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public ExchangeRate setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ExchangeRate setUnit(String unit) {
        this.unit = unit;
        return this;
    }

}
