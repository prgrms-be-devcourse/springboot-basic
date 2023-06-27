package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  private FixedAmountVoucher(UUID voucherId, String voucherType, int discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static FixedAmountVoucher of(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidator.checkFixedDiscountAmount(discountAmount);
    return new FixedAmountVoucher(voucherId, voucherType, discountAmount);
  }

  int discount(int price) {
    return price - discountAmount;
  }

}
