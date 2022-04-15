package org.prgrms.VoucherManagement.voucher.voucher;

import java.util.UUID;

public abstract class Voucher {
  private final UUID voucherId;
  private long reduction;

  protected Voucher(UUID voucherId, long amount) {
    this.voucherId = voucherId;
    this.reduction = amount;
  }

  public UUID getVoucherID() {
    return this.voucherId;
  }

  public long getReduction() {
    return reduction;
  }

  public void setReduction(long reduction) {
    this.reduction = reduction;
  }

  abstract long discount(long beforeDiscount);

}
