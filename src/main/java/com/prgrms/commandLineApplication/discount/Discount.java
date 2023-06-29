package com.prgrms.commandLineApplication.discount;

public abstract class Discount {

  protected final String voucherType;

  public Discount(String voucherType) {
    this.voucherType = voucherType;
  }

  public String getVoucherType() {
    return voucherType;
  }

  abstract int discount(int price);

}
