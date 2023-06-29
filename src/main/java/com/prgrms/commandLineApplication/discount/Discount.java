package com.prgrms.commandLineApplication.discount;

import com.prgrms.commandLineApplication.voucher.VoucherType;

public abstract class Discount {

  protected final VoucherType voucherType;

  public Discount(VoucherType voucherType) {
    this.voucherType = voucherType;
  }

  public VoucherType getVoucherType() {
    return voucherType;
  }

  public abstract int executeDiscount(int price);

  public abstract int getDiscountAmount();

}
