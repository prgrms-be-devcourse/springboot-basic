package com.prgrms.commandLineApplication.voucher.discount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DiscountType {
  FIXED,
  PERCENT;

  private static final Logger LOGGER = LoggerFactory.getLogger(DiscountType.class);

  private static final String VOUCHER_TYPE_ERROR = "Invalid Voucher Type";

  public static DiscountType valueOfType(String type) {
    try {
      return DiscountType.valueOf(type.toUpperCase());
    } catch (Exception e) {
      LOGGER.error("Menu Error Message => {}", VOUCHER_TYPE_ERROR);
      throw new IllegalArgumentException(VOUCHER_TYPE_ERROR);
    }
  }

}
