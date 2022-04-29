package org.prgrms.vouchermanagement.voucher.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
  private final UUID voucherId;
  private long reduction;
  private final LocalDateTime createdAt;

  protected Voucher(UUID voucherId, long amount, LocalDateTime createdAt) {
    this.voucherId = voucherId;
    this.reduction = amount;
    this.createdAt = createdAt;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public long getReduction() {
    return reduction;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setReduction(long reduction) {
    this.reduction = reduction;
  }

  abstract long discount(long beforeDiscount);

}
