package org.programmers.devcourse.voucher.engine.voucher;

import java.text.MessageFormat;
import java.util.UUID;


public class FixedAmountVoucher implements
    Voucher {

  private final UUID voucherId;
  private final long discountAmount;

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
  public long discount(long beforeDiscount) {
    return beforeDiscount - discountAmount;
  }

  @Override
  public String toString() {
    return MessageFormat.format("FixedAmountVoucher : Id = {0}, DiscountAmount = {1}$",
        voucherId,
        discountAmount);
  }
}


