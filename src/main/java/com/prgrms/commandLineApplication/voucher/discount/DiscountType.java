package com.prgrms.commandLineApplication.voucher.discount;

import java.util.NoSuchElementException;

public enum DiscountType {
  FIXED("₩"),
  PERCENT("%");

  private static final String VOUCHER_TYPE_ERROR = "Invalid Voucher Type";
  private static final String VOUCHER_TYPE_UNIT_ERROR = "Invalid Voucher Type Unit";

  private final String unit;

  DiscountType(String unit) {
    this.unit = unit;
  }

  public static DiscountType valueOfType(String type) {
    try {
      return DiscountType.valueOf(type.toUpperCase());
    } catch (Exception e) {
      throw new NoSuchElementException(VOUCHER_TYPE_ERROR);
    }
  }

  public String getUnit(DiscountType discountType) {
    try {
      return DiscountType.valueOf(discountType.name().toUpperCase()).unit;
    } catch (Exception e) {
      throw new NoSuchElementException(VOUCHER_TYPE_UNIT_ERROR);
    }
  }

}
