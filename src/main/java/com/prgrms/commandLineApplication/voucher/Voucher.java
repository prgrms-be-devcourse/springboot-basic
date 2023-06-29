package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public abstract class Voucher {

  protected final UUID voucherId;
  protected final int discountAmount;
  protected final String voucherType;

  protected Voucher(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidator.checkId(voucherId);
    this.voucherId = voucherId;
    this.voucherType = voucherType;
    this.discountAmount = discountAmount;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public int getDiscountAmount() {
    return discountAmount;
  }

  public String getVoucherType() {
    return voucherType;
  }

  abstract int discount(int price);

}
