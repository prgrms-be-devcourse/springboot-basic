package com.example.commandlineapplication.domain.voucher.model;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedAmountVoucher extends Voucher {

  private final UUID voucherId;
  private final long discount;

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  public double discountedPrice(long price) {
    return price - discount;
  }

  @Override
  public VoucherType getVoucherType() {
    return VoucherType.FIXED;
  }
}
