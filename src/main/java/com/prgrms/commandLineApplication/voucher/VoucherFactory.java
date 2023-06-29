package com.prgrms.commandLineApplication.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

  public static Voucher createVoucher(String voucherType, int discountAmount) {
    return switch (VoucherType.valueOfType(voucherType)) {
      case FIXED -> FixedAmountVoucher.of(UUID.randomUUID(), voucherType, discountAmount);
      case PERCENT -> PercentDiscountVoucher.of(UUID.randomUUID(), voucherType, discountAmount);
    };
  }

}
