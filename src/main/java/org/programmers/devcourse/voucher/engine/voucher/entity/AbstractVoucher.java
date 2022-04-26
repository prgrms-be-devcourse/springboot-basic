package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.time.LocalDateTime;

public abstract class AbstractVoucher implements Voucher {

  private final LocalDateTime createdAt;

  protected AbstractVoucher(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Voucher) {
      return this.getVoucherId().equals(((Voucher) obj).getVoucherId());
    }
    return false;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public int hashCode() {
    return this.getVoucherId().hashCode();
  }
}
