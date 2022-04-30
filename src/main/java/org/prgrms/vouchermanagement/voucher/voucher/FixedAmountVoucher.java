package org.prgrms.vouchermanagement.voucher.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  private static final int MAX_REDUCTION = 1_000_000;

  public FixedAmountVoucher(UUID voucherId, long reduction, LocalDateTime createdAt) {
    super(voucherId, reduction, createdAt);
  }

  @Override
  public long discount(long beforeDiscount) {
    long discountResult = beforeDiscount - super.getReduction();
    return 0 < discountResult? discountResult : 0L;
  }

  public static boolean checkReduction(long reduction) {
    if(MAX_REDUCTION < reduction || reduction <= 0) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "FixedAmountVoucher{" +
      "voucherId=" + super.getVoucherId() +
      ", amount=" + super.getReduction() +
      '}';
  }
}
