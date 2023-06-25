package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  public FixedAmountVoucher(UUID voucherId, String voucherType, double discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public double discount(double price) {
    validateDiscountAmount(getDiscountAmount());
    return price - getDiscountAmount();
  }

  void validateDiscountAmount(double price) {
    if (price < getDiscountAmount() || getDiscountAmount() < 0) {
      throw new IllegalArgumentException("Invalid discount amount range (0 ~ price)");
    }
  }

}
