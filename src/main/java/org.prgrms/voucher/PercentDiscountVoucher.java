package org.prgrms.voucher;

import java.util.UUID;


public class PercentDiscountVoucher implements Voucher{
  private final UUID voucherId;
  private final long discountPercent;


  public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
  }

  @Override
  public long discount(long beforeDiscount) {
    return (long)(beforeDiscount * (1 - (discountPercent / 100.0)));
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }
}
