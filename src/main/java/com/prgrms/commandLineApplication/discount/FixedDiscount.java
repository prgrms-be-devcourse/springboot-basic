package com.prgrms.commandLineApplication.discount;

import com.prgrms.commandLineApplication.validator.Validator;

public class FixedDiscount extends Discount {

  private final int discountAmount;
  public static final int MINIMUM_VALUE = 0;
  public static final int MAXIMUM_VALUE = 10_000;

  private FixedDiscount(String voucherType, int discountAmount) {
    super(voucherType);
    this.discountAmount = discountAmount;

  }

  public static FixedDiscount of(String voucherType, int discountAmount) {
    Validator.checkFixedDiscount(discountAmount);
    return new FixedDiscount(voucherType, discountAmount);
  }

  public int getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public int discount(int price) {
    return price - discountAmount;
  }

}
