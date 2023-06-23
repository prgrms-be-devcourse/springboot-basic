package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
  private final UUID voucherId;
  private final double discountAmount;
  private final VoucherType voucherType = VoucherType.FIXED;

  public FixedAmountVoucher(double discountAmount) {
    this.voucherId = UUID.randomUUID();
    this.discountAmount = discountAmount;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public String getVoucherType() {
    return voucherType.getType();
  }

  @Override
  public double getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public double discount(double price) {
    if (isValidDiscountValue(price)) {
      return price - discountAmount;
    }
    return 0;
  }

  public Boolean isValidDiscountValue(double price) {
    if (price < discountAmount || discountAmount < 0) {
      return false;
    }
    return true;
  }

  public Boolean isVoucherIdNull(UUID voucherId) {
    if (voucherId == null) {
      throw new RuntimeException("Invalid ID.\n");
    }
    return true;
  }
}
