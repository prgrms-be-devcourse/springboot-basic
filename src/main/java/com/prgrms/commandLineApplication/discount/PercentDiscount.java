package com.prgrms.commandLineApplication.discount;

import com.prgrms.commandLineApplication.validator.Validator;
import com.prgrms.commandLineApplication.voucher.VoucherType;

public class PercentDiscount extends Discount {

  private final int discountAmount;
  public static final int MINIMUM_VALUE = 0;
  public static final int PERCENT_RATE_BASE = 100;

  private PercentDiscount(int discountAmount) {
    super(VoucherType.PERCENT);
    this.discountAmount = discountAmount;
  }

  public static PercentDiscount of(int discountAmount) {
    Validator.checkPercentDiscount(discountAmount);
    return new PercentDiscount(discountAmount);
  }

  @Override
  public int getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public int executeDiscount(int price) {
    return price - (price * discountAmount / PERCENT_RATE_BASE);
  }

}
