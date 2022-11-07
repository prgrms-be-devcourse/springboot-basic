package org.prgrms.voucher.voucherType;

import java.util.UUID;
import org.prgrms.exception.PaymentCannotBeNegativeException;
import org.prgrms.voucher.discountType.Discount;
import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("fixed")
public class FixedAmountVoucher implements Voucher {

  private final UUID voucherId;
  private final Discount discountAmount;


  public FixedAmountVoucher(UUID voucherId, Discount discountAmount) {
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
  public String toString() {
    return "FixedAmountVoucher : " + discountAmount.getValue() + "원";
  }
}
