package com.prgrms.commandLineApplication.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final long discountValue;

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
    return price - price * (discountValue / 100);
  }
}
