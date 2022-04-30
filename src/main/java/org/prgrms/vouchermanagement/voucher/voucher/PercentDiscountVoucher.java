package org.prgrms.vouchermanagement.voucher.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

  private static final long MAX_REDUCTION = 100L;

  public PercentDiscountVoucher(UUID voucherId, long reduction, LocalDateTime createdAt) {
    super(voucherId, reduction, createdAt);
  }

  @Override
  public long discount(long beforeDiscount) {
    double discountedCost = beforeDiscount * (super.getReduction() / 100.0);
    return (long) (beforeDiscount - discountedCost);
  }

  public static boolean checkReduction(long reduction) {
    if(MAX_REDUCTION < reduction || reduction <= 0) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "PercentDiscountVoucher{" +
      "voucherId=" + super.getVoucherID() +
      ", percent=" + super.getReduction() +
      '}';
  }
}
