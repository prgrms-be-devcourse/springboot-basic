package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PercentDiscountVoucher extends Voucher {

  private static final double MAX_PERCENT = 100;
  private static final long MIN_PERCENT = 0;
  private final UUID voucherId;
  private final long discountPercent;

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public double discountedPrice(long price) {
    checkDiscountPercent();
    return price - price * (discountPercent / MAX_PERCENT);
  }

  private void checkDiscountPercent() {
    if (discountPercent > MAX_PERCENT || discountPercent < MIN_PERCENT) {
      throw new IllegalArgumentException("할인율 범위를 확인해주세요.");
    }
  }

  @Override
  public VoucherType getVoucherType() {
    return VoucherType.PERCENT;
  }
}
