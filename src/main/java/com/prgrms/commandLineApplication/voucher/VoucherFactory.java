package com.prgrms.commandLineApplication.voucher;

import com.prgrms.commandLineApplication.validation.VoucherValidation;

import java.util.UUID;

public class VoucherFactory {

  public static Voucher createVoucher(String voucherType, int discountAmount) {
    VoucherValidation.checkType(voucherType);
    return switch (voucherType) {
      case "fixed" -> FixedAmountVoucher.of(UUID.randomUUID(), voucherType, discountAmount);
      default -> PercentDiscountVoucher.of(UUID.randomUUID(), voucherType, discountAmount);
    };
  }

}
