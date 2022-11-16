package org.prgrms.voucher.voucherType;

import java.util.UUID;
import org.prgrms.exception.PaymentCannotBeNegativeException;
import org.prgrms.voucher.discountType.Amount;

public class FixedAmountVoucher implements Voucher {

  private final UUID voucherId;
  private final Amount discountAmount;


  public FixedAmountVoucher(UUID voucherId, Amount discountAmount) {
    this.voucherId = voucherId;
    this.discountAmount = discountAmount;

  }

  @Override
  public long discount(long beforeDiscount) {
    long discountedAmount = beforeDiscount - discountAmount.getValue();
    if (discountedAmount < 0) {
      throw new PaymentCannotBeNegativeException(discountedAmount);
    }
    return discountedAmount;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public Amount getVoucherAmount() {
    return discountAmount;
  }

  @Override
  public String toString() {
    return "*** FixedAmountVoucher ***"
        + System.lineSeparator()
        + "voucherId : " + voucherId
        + System.lineSeparator()
        + "discountAmount: " + discountAmount.getValue() + "won";
  }
}
