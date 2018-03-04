package com.exchange.rates.utils;

import java.util.Comparator;
import com.exchange.rates.bean.CurrencyRate;

public class CurrencyRateComparator {

  public static final Comparator<CurrencyRate> BY_CODE = new ByCode();
  public static final Comparator<CurrencyRate> BY_DIFF_ASC = new ByDifferenceAsc();
  public static final Comparator<CurrencyRate> BY_DIFF_DESC = new ByDifferenceDesc();
  public static final Comparator<CurrencyRate> BY_ABS_DIFF_ASC = new ByAbsoluteDifferenceAsc();
  public static final Comparator<CurrencyRate> BY_ABS_DIFF_DESC = new ByAbsoluteDifferenceDesc();
  
  private static final int precision = 1000000;

  private static class ByCode implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return o1.getCurrency().getCode().compareTo(o2.getCurrency().getCode());
    }
  }
  
  private static class ByDifferenceAsc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o2.getDifference()*precision - o1.getDifference()*precision));
    }
  }
  
  private static class ByDifferenceDesc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o1.getDifference()*precision - o2.getDifference()*precision));
    }
  }
  
  private static class ByAbsoluteDifferenceAsc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o2.getAbsoluteDifference()*precision - o1.getAbsoluteDifference()*precision));
    }
  }
  
  private static class ByAbsoluteDifferenceDesc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o1.getAbsoluteDifference()*precision - o2.getAbsoluteDifference()*precision));
    }
  }
  
}
