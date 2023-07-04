package com.prgrms.commandLineApplication.voucher.discount;

public abstract class Discount {

  private final DiscountType discountType;

  protected Discount(DiscountType discountType) {
    this.discountType = discountType;
  }

  public DiscountType getDiscountType() {
    return discountType;
  }

  public abstract int executeDiscount(int price);

  public abstract int getDiscountAmount();

}
