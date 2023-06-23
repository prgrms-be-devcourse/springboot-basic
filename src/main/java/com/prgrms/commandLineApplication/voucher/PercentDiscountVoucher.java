package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final long discountValue;
  private final int PERCENT_BASE = 100;

  public PercentDiscountVoucher(UUID voucherId, long discountValue) {
    this.voucherId = voucherId;
    this.discountValue = discountValue;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public long discount(long price) {
    if (isValidDiscountValue()) {
      return price - (discountValue / PERCENT_BASE) * price;
    }
    return 0;
  }

  public Boolean isValidDiscountValue() {
    if (PERCENT_BASE < discountValue || discountValue < 0) {
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
