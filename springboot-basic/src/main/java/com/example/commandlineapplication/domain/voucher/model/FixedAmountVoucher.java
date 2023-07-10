package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedAmountVoucher extends Voucher {

  private final UUID voucherId;
  private final long discount;
  private final long MIN_DISCOUNTED_PRICE = 0L;

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public VoucherType getVoucherType() {
    return VoucherType.FIXED;
  }

  public double discountedPrice(long price) {
    return checkDiscountedPrice(price);
  }

  private long checkDiscountedPrice(long price) {
    if (price < discount) {
      return MIN_DISCOUNTED_PRICE;
    }
    return price - discount;
  }
}
