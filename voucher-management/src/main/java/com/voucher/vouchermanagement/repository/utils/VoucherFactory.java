package com.voucher.vouchermanagement.repository.utils;

import com.voucher.vouchermanagement.model.voucher.FixedAmountVoucher;
import com.voucher.vouchermanagement.model.voucher.PercentDiscountVoucher;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

  public Voucher createVoucher(UUID id, Long value, VoucherType voucherType) {
    if (voucherType.equals(VoucherType.FixedAmountVoucher)) {
      return new FixedAmountVoucher(id, value, LocalDateTime.now());
    } else if (voucherType.equals(VoucherType.PercentDiscountVoucher)) {
      return new PercentDiscountVoucher(id, value, LocalDateTime.now());
    }

    return null;
  }
}
