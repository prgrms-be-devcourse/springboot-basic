package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.dto.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.model.FixedAmountVoucher;
import com.example.commandlineapplication.domain.voucher.model.PercentDiscountVoucher;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherFactory {

  private final static Logger log = LoggerFactory.getLogger(VoucherFactory.class);

  public VoucherFactory() {
  }

  public Voucher create(VoucherCreateRequest request) {
    VoucherType voucherType = request.getVoucherType();

    return toVoucher(voucherType, UUID.randomUUID(), request.getDiscountAmount());
  }

  public Voucher toVoucher(VoucherType voucherType, UUID voucherId, long amount) {
    switch (voucherType) {
      case FIXED:
        return new FixedAmountVoucher(voucherId, amount);

      case PERCENT:
        return new PercentDiscountVoucher(voucherId, amount);

      default:
        log.error("잘못된 바우처 유형입니다.");
        throw new IllegalArgumentException("잘못된 바우처 유형입니다.");
    }
  }
}
