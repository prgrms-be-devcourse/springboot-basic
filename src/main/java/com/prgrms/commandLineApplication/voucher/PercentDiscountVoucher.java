package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final double discountAmount;
  private final VoucherType voucherType = VoucherType.PERCENT;
  private final int PERCENT_BASE = 100;

  public PercentDiscountVoucher(double discountAmount) {
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
    if (isValidDiscountValue()) {
      return price - (discountAmount / PERCENT_BASE) * price;
    }
    return 0;
  }

  public Boolean isValidDiscountValue() {
    if (PERCENT_BASE < discountAmount || discountAmount < 0) {
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
