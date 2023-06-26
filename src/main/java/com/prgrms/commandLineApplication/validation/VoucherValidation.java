package com.prgrms.commandLineApplication.validation;

import java.util.UUID;

public class VoucherValidation {
  private static final int MINIMUM_VALUE = 0;
  private static final int PERCENT_RATE_BASE = 100;

  public static void checkId(UUID voucherId) {
    if (voucherId == null) {
      throw new IllegalArgumentException("Invalid ID");
    }
  }

  public static void checkFixedDiscountAmount(int discountAmount) {
    if (discountAmount < MINIMUM_VALUE) {
      throw new IllegalArgumentException("Invalid discount amount range (0 ~ )");
    }
  }

  public static void checkPercentDiscountAmount(int discountAmount) {
    if (PERCENT_RATE_BASE < discountAmount || discountAmount < MINIMUM_VALUE) {
      throw new IllegalArgumentException("Invalid discount amount range (0 ~ 100)");
    }
  }

  public static void checkType(String voucherType) {
    if (!voucherType.equals("fixed") || !voucherType.equals("percent")) {
      throw new IllegalArgumentException("Invalid Type");
    }
  }
}
