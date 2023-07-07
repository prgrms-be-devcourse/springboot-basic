package com.prgrms.commandLineApplication.voucher.discount;

import java.io.IOException;

public class DiscountFactory {

  public static Discount of(String voucherType, int discountAmount) throws IOException {
    return switch (DiscountType.valueOfType(voucherType)) {
      case FIXED -> FixedDiscount.of(discountAmount);
      case PERCENT -> PercentDiscount.of(discountAmount);
    };
  }

}
