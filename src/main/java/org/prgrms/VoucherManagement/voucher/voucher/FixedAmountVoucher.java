package org.prgrms.VoucherManagement.voucher.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

  private static final long MAX_REDUCTION = 1_000_000L;

  public FixedAmountVoucher(UUID voucherId, long reduction) {
    super(voucherId, reduction);
  }

  @Override
  public long discount(long beforeDiscount) {
    long discountResult = beforeDiscount - super.getReduction();
    return 0 < discountResult? discountResult : 0L;
  }

  static boolean checkReduction(long reduction) {
    if(MAX_REDUCTION < reduction || reduction <= 0) return false;
    return true;
  }


  @Override
  public String toString() {
    return "FixedAmountVoucher{" +
      "voucherId=" + super.getVoucherID() +
      ", amount=" + super.getReduction() +
      '}';
  }
}
