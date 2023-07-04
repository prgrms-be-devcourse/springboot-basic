package com.prgrms.commandLineApplication.voucher.discount;

public abstract class Discount {

  public abstract int executeDiscount(int price);

  public abstract DiscountType getDiscountType();

  public abstract int getDiscountAmount();

}
