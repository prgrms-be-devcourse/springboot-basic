package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public abstract class Voucher {

  private final UUID voucherId;
  private final double discountAmount;
  private final String voucherType;

  protected Voucher(UUID voucherId, String voucherType, double discountAmount) {
    validateVoucherId(voucherId);
    this.voucherId = voucherId;
    this.voucherType = voucherType;
    this.discountAmount = discountAmount;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public double getDiscountAmount() {
    return discountAmount;
  }

  public String getVoucherType() {
    return voucherType;
  }

  public void validateVoucherId(UUID voucherId) {
    if (voucherId == null) {
      throw new IllegalArgumentException("Invalid ID");
    }
  }

  abstract double discount(double price);

  abstract void validateDiscountAmount(double discountAmount);

}
