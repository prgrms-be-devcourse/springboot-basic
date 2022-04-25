package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.text.MessageFormat;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDiscountDegreeOutOfRangeException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherFactory;


public class FixedAmountVoucher extends
    AbstractVoucher {

  private static final long MAX_AMOUNT = 1000000;
  public static final VoucherFactory factory = FixedAmountVoucher::new;
  private final UUID voucherId;
  private final long discountAmount;

  private FixedAmountVoucher(UUID voucherId, long discountAmount)
      throws VoucherDiscountDegreeOutOfRangeException {
    if (discountAmount >= MAX_AMOUNT) {
      throw new VoucherDiscountDegreeOutOfRangeException(
          MessageFormat.format("discount amount must be lower than {0}", MAX_AMOUNT));
    }
    if (discountAmount <= 0) {
      throw new VoucherDiscountDegreeOutOfRangeException(
          "discount amount must have positive value");
    }
    this.voucherId = voucherId;
    this.discountAmount = discountAmount;
  }


  @Override
  public long getDiscountDegree() {
    return discountAmount;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public long discount(long beforeDiscount) {
    return beforeDiscount > this.discountAmount ? beforeDiscount
        - this.discountAmount : 0;
  }

  @Override
  public String toString() {
    return MessageFormat.format("FixedAmountVoucher : Id = {0}, DiscountAmount = {1}$",
        voucherId,
        discountAmount);
  }


}


