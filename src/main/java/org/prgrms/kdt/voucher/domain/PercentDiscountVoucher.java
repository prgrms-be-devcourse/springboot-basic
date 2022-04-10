package org.prgrms.kdt.voucher.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
  private static final Long MAX_PERCENTAGE = 100L;
  private static final Long MIN_PERCENTAGE = 0L;
  private final UUID voucherId;
  private final Long percent;

  public PercentDiscountVoucher(UUID voucherId, Long percent) {
    if (percent <= MIN_PERCENTAGE || percent > MAX_PERCENTAGE) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100");
    }
    this.voucherId = voucherId;
    this.percent = percent;
  }

  public PercentDiscountVoucher(long percent) {
    this(UUID.randomUUID(), percent);
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  public long discount(long beforeDiscount) {
    return beforeDiscount * (percent / MAX_PERCENTAGE);
  }

  @Override
  public String toString() {
    return MessageFormat.format("PercentDiscountVoucher [voucherId={0}, percent={1}]",
        voucherId,
        percent);
  }
}