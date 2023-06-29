package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

class VoucherValidator {

  private static final String INVALID_ID_ERROR = "Invalid ID";
  private static final String INVALID_FIXED_AMOUNT_RANGE_ERROR = "Invalid discount amount range (0 ~ 10000)";
  private static final String INVALID_PERCENT_AMOUNT_ERROR = "Invalid discount amount range (0 ~ 100)";

  public static void checkId(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException(INVALID_ID_ERROR);
    }
  }

  public static void checkFixedDiscountAmount(int discountAmount) {
    if (discountAmount < FixedAmountVoucher.MINIMUM_VALUE || discountAmount > FixedAmountVoucher.MAXIMUM_VALUE) {
      throw new IllegalArgumentException(INVALID_FIXED_AMOUNT_RANGE_ERROR);
    }
  }

  public static void checkPercentDiscountAmount(int discountAmount) {
    if (PercentDiscountVoucher.PERCENT_RATE_BASE < discountAmount || discountAmount < PercentDiscountVoucher.MINIMUM_VALUE) {
      throw new IllegalArgumentException(INVALID_PERCENT_AMOUNT_ERROR);
    }
  }

}
