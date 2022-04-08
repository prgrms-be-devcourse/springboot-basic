package org.programmers.devcourse.voucher.engine.voucher;

import java.text.MessageFormat;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;


public class FixedAmountVoucher implements
    Voucher {

  private static final long MAX_AMOUNT = 1000000;
  public static final VoucherFactory factory = FixedAmountVoucher::new;
  private final UUID voucherId;
  private final long discountAmount;

  private FixedAmountVoucher(UUID voucherId, long discountAmount)
      throws VoucherDataOutOfRangeException {
    if (discountAmount >= MAX_AMOUNT) {
      throw new VoucherDataOutOfRangeException(
          MessageFormat.format("discount amount must be lower than {0}", MAX_AMOUNT));
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
    return beforeDiscount - discountAmount;
  }

  @Override
  public String toString() {
    return MessageFormat.format("FixedAmountVoucher : Id = {0}, DiscountAmount = {1}$",
        voucherId,
        discountAmount);
  }


}


