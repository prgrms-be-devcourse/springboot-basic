package com.prgrms.commandLineApplication.voucher.discount;

import com.prgrms.commandLineApplication.validator.Validator;
import com.prgrms.commandLineApplication.voucher.VoucherType;

public class FixedDiscount extends Discount {

  private final int discountAmount;
  public static final int MINIMUM_VALUE = 0;
  public static final int MAXIMUM_VALUE = 10_000;

  private FixedDiscount(int discountAmount) {
    super(VoucherType.FIXED);
    this.discountAmount = discountAmount;
  }

  public static FixedDiscount of(int discountAmount) {
    Validator.checkFixedDiscount(discountAmount);
    return new FixedDiscount(discountAmount);
  }

  @Override
  public int getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public int executeDiscount(int price) {
    return price - discountAmount;
  }

}
