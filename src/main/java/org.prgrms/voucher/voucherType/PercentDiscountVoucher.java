package org.prgrms.voucher.voucherType;

import java.util.UUID;
import org.prgrms.voucher.discountType.Amount;

public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final Amount discountPercent;
  private final VoucherType type;

  public PercentDiscountVoucher(UUID voucherId, Amount discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
    this.type = VoucherType.PERCENT;
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
  public Amount getVoucherAmount() {
    return discountPercent;
  }

  @Override
  public VoucherType getVoucherType() {
    return type;
  }

  @Override
  public String toString() {
    return "*** PercentDiscountVoucher ***"
        + System.lineSeparator()
        + "voucherId : " + voucherId
        + System.lineSeparator()
        + "discountRate: " + discountPercent.getValue() + "%";
  }
}
