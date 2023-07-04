package com.prgrms.commandLineApplication.voucher.discount;

public class DiscountFactory {

  public static Discount createDiscount(String voucherType, int discountAmount) {
    return switch (DiscountType.valueOfType(voucherType)) {
      case FIXED -> FixedDiscount.of(discountAmount);
      case PERCENT -> PercentDiscount.of(discountAmount);
    };
  }

}
