package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

  private static final int PERCENT_RATE_BASE = 100;

  private PercentDiscountVoucher(UUID voucherId, String voucherType, int discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static PercentDiscountVoucher of(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidation.checkPercentDiscountAmount(discountAmount);
    return new PercentDiscountVoucher(voucherId, voucherType, discountAmount);
  }

  public int discount(int price) {
    int result = price - (price * getDiscountAmount() / PERCENT_RATE_BASE);
    return result;
  }

}
