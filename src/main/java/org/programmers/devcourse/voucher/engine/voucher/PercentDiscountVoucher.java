package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements
    Voucher {

  private final UUID voucherId;
  private final String type = "PercentDiscount";
  private final long discountPercent;

  private PercentDiscountVoucher(UUID voucherId, long discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
  }

  public static PercentDiscountVoucher from(long discountPercent) {
    return new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
  }


  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public long discount(long beforeDiscount) {
    return beforeDiscount * (100 - discountPercent) / 100;
  }
}
