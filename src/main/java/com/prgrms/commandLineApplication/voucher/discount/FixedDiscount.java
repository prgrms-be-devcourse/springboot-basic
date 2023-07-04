package com.prgrms.commandLineApplication.voucher.discount;

import com.prgrms.commandLineApplication.voucher.discount.validator.DiscountValidator;

public class FixedDiscount extends Discount {

  public static final int MINIMUM_VALUE = 0;
  public static final int MAXIMUM_VALUE = 10_000;

  private static final DiscountType discountType = DiscountType.FIXED;
  private final int discountAmount;

  private FixedDiscount(int discountAmount) {
    this.discountAmount = discountAmount;
  }

  public static FixedDiscount of(int discountAmount) {
    DiscountValidator.checkFixedDiscount(discountAmount);
    return new FixedDiscount(discountAmount);
  }

  @Override
  public int getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public DiscountType getDiscountType() {
    return discountType;
  }

  @Override
  public int executeDiscount(int price) {
    return price - discountAmount;
  }

}
