package org.prgrms.kdt.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Voucher implements Serializable {

  protected final UUID voucherId;
  protected UUID customerId;

  protected Voucher(UUID voucherId, UUID customerId) {
    this.voucherId = voucherId;
    this.customerId = customerId;
  }

  protected Voucher(UUID voucherId) {
    this(voucherId, null);
  }

  public void assign(UUID customerId) {
    this.customerId = customerId;
  }

  public UUID getVoucherId() {
    return voucherId;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public abstract Long getAmount();

  public abstract int getType();

  public abstract long discount(long beforeDiscount);

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Voucher voucher = (Voucher) o;
    return Objects.equals(voucherId, voucher.voucherId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(voucherId);
  }
}