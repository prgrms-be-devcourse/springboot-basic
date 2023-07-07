package com.prgrms.commandLineApplication.voucher.discount;

import java.io.IOException;

public enum DiscountType {
  FIXED,
  PERCENT;

  private static final String VOUCHER_TYPE_ERROR = "Invalid Voucher Type";

  public static DiscountType valueOfType(String type) throws IOException {
    try {
      return DiscountType.valueOf(type.toUpperCase());
    } catch (Exception e) {
      throw new IOException(VOUCHER_TYPE_ERROR);
    }
  }

  public static String getUnit(DiscountType discountType) {
    return DiscountType.FIXED == discountType ? "₩" : "%";
  }

}
