package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
  private static final int MINIMUM_VALUE = 0;

  private FixedAmountVoucher(UUID voucherId, String voucherType, double discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static FixedAmountVoucher of(UUID voucherId, String voucherType, double discountAmount) {
    validateDiscountAmount(discountAmount);
    return new FixedAmountVoucher(voucherId, voucherType, discountAmount);
  }

  public double discount(double price) {
    return price - getDiscountAmount();
  }

  void validateDiscountAmount(double discountAmount) {
    if (discountAmount < MINIMUM_VALUE) {
      throw new IllegalArgumentException("Invalid discount amount range (0 ~ )");
    }
  }

}
