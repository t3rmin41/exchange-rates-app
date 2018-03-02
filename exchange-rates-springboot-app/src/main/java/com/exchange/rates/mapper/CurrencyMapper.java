package com.exchange.rates.mapper;

import java.util.List;
import com.exchange.rates.bean.Currency;

public interface CurrencyMapper {

  List<Currency> getAllCurrencies();
}
