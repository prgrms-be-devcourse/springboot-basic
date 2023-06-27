package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public abstract class Voucher {

  protected final UUID voucherId;
  protected final int discountAmount;
  protected final String voucherType;

  protected Voucher(UUID voucherId, String voucherType, int discountAmount) {
    VoucherValidation.checkId(voucherId);
    this.voucherId = voucherId;
    this.voucherType = voucherType;
    this.discountAmount = discountAmount;
  }

  abstract int discount(int price);

}
