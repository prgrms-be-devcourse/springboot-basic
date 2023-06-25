package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
  private static final int PERCENT_RATE_BASE = 100;
  private static final int MINIMUM_VALUE = 0;

  public PercentDiscountVoucher(UUID voucherId, String voucherType, double discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public double discount(double price) {
    validateDiscountAmount(getDiscountAmount());
    return price - (getDiscountAmount() / PERCENT_RATE_BASE) * price;
  }

  void validateDiscountAmount(double discountAmount) {
    if (PERCENT_RATE_BASE < discountAmount || discountAmount < MINIMUM_VALUE) {
      throw new IllegalArgumentException("Invalid discount amount range (0 ~ 100)");
    }
  }

}
