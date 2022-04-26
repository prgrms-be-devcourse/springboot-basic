package org.prgrms.kdt.domain;

import java.text.MessageFormat;
import java.util.UUID;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.type.VoucherType;

public class PercentDiscountVoucher extends Voucher {

  private static final Long MAX_PERCENTAGE = 100L;
  private static final Long MIN_PERCENTAGE = 0L;
  private final Long percent;

  public PercentDiscountVoucher(UUID voucherId, UUID customerId, Long percent) {
    super(voucherId, customerId);
    if (percent <= MIN_PERCENTAGE || percent > MAX_PERCENTAGE) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100");
    }
    this.percent = percent;
  }

  public PercentDiscountVoucher(VoucherDto voucherDto) {
    this(voucherDto.voucherId(), voucherDto.customerId(), voucherDto.amount());
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public Long getAmount() {
    return percent;
  }

  @Override
  public int getType() {
    return VoucherType.PERCENT.getCode();
  }

  @Override
  public UUID getCustomerId() {
    return customerId;
  }

  @Override
  public void assign(UUID customerId) {
    this.customerId = customerId;
  }

  public long discount(long beforeDiscount) {
    return beforeDiscount * (percent / MAX_PERCENTAGE);
  }

  @Override
  public String toString() {
    return MessageFormat.format("Voucher: [voucherId={0}, percent={1}, type={2}, customerId={3}]",
        voucherId,
        percent,
        VoucherType.PERCENT.name(),
        customerId);
  }
}