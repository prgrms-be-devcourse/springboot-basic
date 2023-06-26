package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  private FixedAmountVoucher(UUID voucherId, String voucherType, int discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static FixedAmountVoucher of(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidation.checkFixedDiscountAmount(discountAmount);
    return new FixedAmountVoucher(voucherId, voucherType, discountAmount);
  }

  public int discount(int price) {
    int result = price - getDiscountAmount();
    return result;

  }

}
