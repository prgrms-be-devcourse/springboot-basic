package org.programmers.devcourse.voucher.engine.voucher.entity;

public abstract class AbstractVoucher implements Voucher {

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
  public int hashCode() {
    return this.getVoucherId().hashCode();
  }
}
