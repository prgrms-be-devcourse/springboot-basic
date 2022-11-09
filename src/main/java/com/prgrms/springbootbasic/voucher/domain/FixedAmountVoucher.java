package com.prgrms.springbootbasic.voucher.domain;

import java.math.BigDecimal;
import java.util.UUID;

//convention
public class FixedAmountVoucher implements Voucher {

  UUID id;
  String voucherType;
  int fixedAmount;

  public FixedAmountVoucher(String voucherType, int fixedAmount) {
    this.id = UUID.randomUUID();
    this.voucherType = voucherType;
    this.fixedAmount = fixedAmount;
  }

  @Override
  public String getVoucherType() {
    return voucherType;
  }

  @Override
  public UUID getUUID() {
    return id;
  }

  @Override
  public int getDiscountRate() {
    return fixedAmount;
  }

  @Override
  public BigDecimal discount(int price) {
    int result = price - fixedAmount;
    if (result <= 0) {
      throw new IllegalArgumentException(
          "Wrong price. You must order more expensive item then " + fixedAmount);
    }
    return BigDecimal.valueOf(result);
  }
}
