package com.prgrms.commandLineApplication.validator;

import com.prgrms.commandLineApplication.voucher.discount.FixedDiscount;
import com.prgrms.commandLineApplication.voucher.discount.PercentDiscount;

import java.util.UUID;

public class Validator {

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_FIXED_AMOUNT_RANGE_ERROR = "Invalid discount amount range (0 ~ 10000)";
  private static final String INVALID_PERCENT_AMOUNT_ERROR = "Invalid discount amount range (0 ~ 100)";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkFixedDiscount(int discountAmount) {
    if (discountAmount < FixedDiscount.MINIMUM_VALUE || discountAmount > FixedDiscount.MAXIMUM_VALUE) {
      throw new IllegalArgumentException(INVALID_FIXED_AMOUNT_RANGE_ERROR);
    }
  }

  public static void checkPercentDiscount(int discountAmount) {
    if (PercentDiscount.PERCENT_RATE_BASE < discountAmount || discountAmount < PercentDiscount.MINIMUM_VALUE) {
      throw new IllegalArgumentException(INVALID_PERCENT_AMOUNT_ERROR);
    }
  }

}
