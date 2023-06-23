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
    return price - (discountValue / PERCENT_BASE) * price;
  }
}
