package com.prgrms.commandLineApplication.voucher;

public class FixedAmountVoucher extends Voucher {

  public FixedAmountVoucher(String voucherType, double discountAmount) {
    super(voucherType, discountAmount);
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
