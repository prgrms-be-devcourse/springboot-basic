package org.programs.kdt.Voucher.domain;

import lombok.Getter;
import lombok.ToString;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Exception.InvalidValueException;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class FixedAmountVoucher implements Voucher {

  private final UUID voucherId;
  private long value;
  private final LocalDateTime createdAt;

  public FixedAmountVoucher(UUID voucherId, long value, LocalDateTime createdAt) {
    validation(value);
    this.voucherId = voucherId;
    this.value = value;
    this.createdAt = createdAt;
  }

  public FixedAmountVoucher(UUID voucherId, long value) {
    validation(value);
    this.voucherId = voucherId;
    this.value = value;
    createdAt = LocalDateTime.now();
  }

  @Override
  public long discount(long beforeDiscount) {
    long result = beforeDiscount - value;
    return result < 0 ? 0 : result;
  }

  @Override
  public VoucherType getVoucherType() {
    return VoucherType.FIXEDAMOUNT;
  }

  @Override
  public void validation (long value) {
    if (value <= 0 || value > getMaxValue()) {
      throw new InvalidValueException(ErrorCode.INVALID_VOUCHER_VALUE);
    }
  }

  @Override
  public long getMaxValue() {
    return 10000;
  }

  @Override
  public void changeValue(Long value) {
    validation(value);
    this.value = value;
  }
}
