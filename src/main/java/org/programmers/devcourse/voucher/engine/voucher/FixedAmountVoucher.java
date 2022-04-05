package org.programmers.devcourse.voucher.engine.voucher;

import java.util.UUID;


public class FixedAmountVoucher implements
    Voucher {

  private final UUID voucherId;
  private final long discountAmount;
  private final String type = "FixedAmount";

  private FixedAmountVoucher(UUID voucherId, long discountAmount) {
    this.voucherId = voucherId;
    this.discountAmount = discountAmount;
  }

  public static FixedAmountVoucher from(long discountAmount) {
    return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
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
    return beforeDiscount - discountAmount;
  }
}


