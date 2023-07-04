package com.prgrms.commandLineApplication.voucher.discount;

public enum DiscountType {
  FIXED,
  PERCENT;

  private static final String ERROR_MESSAGE = "Invalid Voucher Type";

  public static DiscountType valueOfType(String type) {
    try {
      return DiscountType.valueOf(type.toUpperCase());
    } catch (Exception e) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
  }

}
