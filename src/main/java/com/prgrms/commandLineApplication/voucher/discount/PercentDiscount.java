package com.prgrms.commandLineApplication.voucher.discount;

import com.prgrms.commandLineApplication.voucher.discount.validator.DiscountValidator;

public class PercentDiscount extends Discount {

  public static final int MINIMUM_VALUE = 0;
  public static final int PERCENT_RATE_BASE = 100;

  private final int discountAmount;

  private PercentDiscount(int discountAmount) {
    super(DiscountType.PERCENT);
    this.discountAmount = discountAmount;
  }

  public static PercentDiscount of(int discountAmount) {
    DiscountValidator.checkPercentDiscount(discountAmount);
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
