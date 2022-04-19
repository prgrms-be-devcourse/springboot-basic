package org.prgrms.springbootbasic.voucher.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
  private final UUID voucherId;
  private final long percent;
  private static final long divider = 100L;

  public PercentDiscountVoucher(UUID voucherId, long percent) {
    this.voucherId = voucherId;
    this.percent = percent;
  }

  @Override
  public UUID getVoucherID() {
    return voucherId;
  }

  @Override
  public long discount(long beforeDiscount) {
    return beforeDiscount * (percent / divider);
  }

  @Override
  public String toString() {
    return "PercentDiscountVoucher{" +
      "voucherId=" + voucherId +
      ", percent=" + percent +
      '}';
  }
}