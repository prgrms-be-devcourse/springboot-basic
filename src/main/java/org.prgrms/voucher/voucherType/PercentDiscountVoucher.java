package org.prgrms.voucher.voucherType;

import java.util.UUID;
import org.prgrms.voucher.discountType.Discount;


public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final Discount discountPercent;


  public PercentDiscountVoucher(UUID voucherId, Discount discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
  }

  @Override
  public long discount(long beforeDiscount) {
    return (long)(beforeDiscount * (1 - (discountPercent.getValue() / 100.0)));
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public String toString() {
    return "PercentDiscountVoucher : " + discountPercent.getValue() + "%";
  }
}
