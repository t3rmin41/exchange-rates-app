package com.exchange.rates.utils;

import java.util.Comparator;
import com.exchange.rates.bean.CurrencyRate;

public class CurrencyRateComparator {

  public static final Comparator<CurrencyRate> BY_CODE = new ByCode();
  public static final Comparator<CurrencyRate> BY_DIFF_ASC = new ByDifferenceAsc();
  public static final Comparator<CurrencyRate> BY_DIFF_DESC = new ByDifferenceDesc();
  public static final Comparator<CurrencyRate> BY_ABS_DIFF_ASC = new ByAbsoluteDifferenceAsc();
  public static final Comparator<CurrencyRate> BY_ABS_DIFF_DESC = new ByAbsoluteDifferenceDesc();

  private static class ByCode implements Comparator<CurrencyRate> {
    public static final String BY_CODE = "BY_CODE";
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return o1.getCurrency().getCode().compareTo(o2.getCurrency().getCode());
    }
  }
  
  private static class ByDifferenceAsc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o2.getDifference()*CurrencyRate.PRECISION - o1.getDifference()*CurrencyRate.PRECISION));
    }
  }
  
  private static class ByDifferenceDesc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o1.getDifference()*CurrencyRate.PRECISION - o2.getDifference()*CurrencyRate.PRECISION));
    }
  }
  
  private static class ByAbsoluteDifferenceAsc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o2.getAbsoluteDifference()*CurrencyRate.PRECISION - o1.getAbsoluteDifference()*CurrencyRate.PRECISION));
    }
  }
  
  private static class ByAbsoluteDifferenceDesc implements Comparator<CurrencyRate> {
    @Override
    public int compare(CurrencyRate o1, CurrencyRate o2) {
      return (int) ((o1.getAbsoluteDifference()*CurrencyRate.PRECISION - o2.getAbsoluteDifference()*CurrencyRate.PRECISION));
    }
  }
  
}
