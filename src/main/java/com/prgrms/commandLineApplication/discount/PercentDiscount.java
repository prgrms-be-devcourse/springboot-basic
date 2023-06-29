package com.prgrms.commandLineApplication.discount;

import com.prgrms.commandLineApplication.validator.Validator;

public class PercentDiscount extends Discount {

  private final int discountAmount;
  public static final int MINIMUM_VALUE = 0;
  public static final int PERCENT_RATE_BASE = 100;

  private PercentDiscount(String voucherType, int discountAmount) {
    super(voucherType);
    this.discountAmount = discountAmount;
  }

  public static PercentDiscount of(String voucherType, int discountAmount) {
    Validator.checkPercentDiscount(discountAmount);
    return new PercentDiscount(voucherType, discountAmount);
  }

  public int getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public int discount(int price) {
    return price - (price * discountAmount / PERCENT_RATE_BASE);
  }

}
