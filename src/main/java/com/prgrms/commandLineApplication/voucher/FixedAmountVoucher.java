package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
  private final UUID voucherId;
  private final long discountAmount;
  private final VoucherType voucherType = VoucherType.FIXED;

  public FixedAmountVoucher(long discountAmount) {
    this.voucherId = UUID.randomUUID();
    this.discountAmount = discountAmount;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public String getVoucherType() {
    return voucherType.getType();
  }

  @Override
  public long getDiscountAmount() {
    return discountAmount;
  }

  @Override
  public long discount(long price) {
    if (isValidDiscountValue(price)) {
      return price - discountAmount;
    }
    return 0;
  }

  public Boolean isValidDiscountValue(long price) {
    if (price < discountAmount || discountAmount < 0) {
      return false;
    }
    return true;
  }

  public Boolean isVoucherIdNull(UUID voucherId) {
    if (voucherId == null) {
      throw new RuntimeException("Invalid ID.\n");
    }
    return true;
  }
}
