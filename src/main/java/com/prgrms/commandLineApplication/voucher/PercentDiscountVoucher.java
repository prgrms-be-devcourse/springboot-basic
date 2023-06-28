package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

  protected static final int MINIMUM_VALUE = 0;
  protected static final int PERCENT_RATE_BASE = 100;

  private PercentDiscountVoucher(UUID voucherId, String voucherType, int discountAmount) {
    super(voucherId, voucherType, discountAmount);
  }

  public static PercentDiscountVoucher of(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidator.checkPercentDiscountAmount(discountAmount);
    return new PercentDiscountVoucher(voucherId, voucherType, discountAmount);
  }

  public int discount(int price) {
    return price - (price * discountAmount / PERCENT_RATE_BASE);
  }

}
