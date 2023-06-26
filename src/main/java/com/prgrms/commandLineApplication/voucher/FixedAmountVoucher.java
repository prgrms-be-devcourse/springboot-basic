package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  private FixedAmountVoucher(UUID voucherId, String voucherType, double discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static FixedAmountVoucher of(UUID voucherId, String voucherType, double discountAmount) {
    VoucherValidation.checkFixedDiscountAmount(discountAmount);
    return new FixedAmountVoucher(voucherId, voucherType, discountAmount);
  }

  public double discount(double price) {
    return price - getDiscountAmount();
  }

}
