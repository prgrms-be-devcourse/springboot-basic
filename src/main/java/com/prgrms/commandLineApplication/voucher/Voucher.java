package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public abstract class Voucher {

  private final UUID voucherId;
  private final int discountAmount;
  private final String voucherType;

  protected Voucher(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidation.checkId(voucherId);
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
