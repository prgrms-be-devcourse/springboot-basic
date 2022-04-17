package org.prgrms.kdt.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

  private final UUID voucherId;
  private final long amount;

  public FixedAmountVoucher(UUID voucherId, long amount) {
    if(amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
    this.voucherId = voucherId;
    this.amount = amount;
  }

  public FixedAmountVoucher(long amount) {
    this(UUID.randomUUID(), amount);
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  public long discount(long beforeDiscount) {
    return beforeDiscount - amount;
  }

  @Override
  public String toString() {
    return MessageFormat.format("FixedAmountVoucher [voucherId={0}, amount={1}]",
        voucherId,
        amount);
  }
}