package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
  private static final int PERCENT_RATE_BASE = 100;

  private PercentDiscountVoucher(UUID voucherId, String voucherType, double discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static PercentDiscountVoucher of(UUID voucherId, String voucherType, double discountAmount) {
    VoucherValidation.checkPercentDiscountAmount(discountAmount);
    return new PercentDiscountVoucher(voucherId, voucherType, discountAmount);
  }

  public double discount(double price) {
    return price - (getDiscountAmount() / PERCENT_RATE_BASE) * price;
  }

}
