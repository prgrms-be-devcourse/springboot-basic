package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {
  private final UUID voucherId;
  private final long discountPercent;

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public long discountedPrice(long price) {
    return price * (discountPercent / 100);
  }
}
