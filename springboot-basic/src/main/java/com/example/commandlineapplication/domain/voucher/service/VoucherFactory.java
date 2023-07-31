package com.example.commandlineapplication.domain.voucher.service;

import com.example.commandlineapplication.domain.voucher.FixedAmountVoucher;
import com.example.commandlineapplication.domain.voucher.PercentDiscountVoucher;
import com.example.commandlineapplication.domain.voucher.Voucher;
import com.example.commandlineapplication.domain.voucher.VoucherType;
import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherFactory {

  private static final Logger LOG = LoggerFactory.getLogger(VoucherFactory.class);
  private static final long MAX_PERCENT = 100L;
  private static final long MIN_PERCENT = 0L;
  private static final long MIN_FIXED_AMOUNT = 0L;

  public Voucher create(VoucherCreateRequest request) {
    return toVoucher(request.getVoucherType(), request.getVoucherId(), request.getDiscountAmount());
  }

  public Voucher toVoucher(VoucherType voucherType, UUID voucherId, long amount) {
    switch (voucherType) {
      case FIXED:
        LOG.info("FixedAmountVoucher가 생성되었습니다.");
        validateFixedAmount(amount);
        return new FixedAmountVoucher(voucherId, amount);

      case PERCENT:
        LOG.info("PercentDiscountVoucher가 생성되었습니다.");
        validatePercentAmount(amount);
        return new PercentDiscountVoucher(voucherId, amount);

      default:
        LOG.warn("잘못된 바우처 유형입니다.");
        throw new IllegalArgumentException("잘못된 바우처 유형입니다.");
    }
  }

  public void validatePercentAmount(long amount) {
    if (amount < MIN_PERCENT || amount > MAX_PERCENT) {
      LOG.warn("amount 범위를 확인해주세요.");
      throw new IllegalArgumentException("amount 범위를 확인해주세요.");
    }
  }

  public void validateFixedAmount(long amount) {
    if (amount < MIN_FIXED_AMOUNT) {
      LOG.warn("amount는 음수가 될 수 없습니다.");
      throw new IllegalArgumentException("amount 범위를 확인해주세요.");
    }
  }
}