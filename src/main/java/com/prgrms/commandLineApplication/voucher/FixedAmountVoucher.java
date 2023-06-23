package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
  private final UUID voucherId;
  private final long discountValue;

  public FixedAmountVoucher(UUID voucherId, long discountValue) {
    this.voucherId = voucherId;
    this.discountValue = discountValue;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public long getDiscountValue() {
    return discountValue;
  }

  @Override
  public long discount(long price) {
    if (isValidDiscountValue(price)) {
      return price - discountValue;
    }
    return 0;
  }

  public Boolean isValidDiscountValue(long price) {
    if (price < discountValue || discountValue < 0) {
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
