package org.prgrms.kdt.domain;

import java.text.MessageFormat;
import java.util.UUID;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.type.VoucherType;

public class FixedAmountVoucher extends Voucher {

  private final Long amount;

  public FixedAmountVoucher(UUID voucherId, UUID customerId, Long amount) {
    super(voucherId, customerId);
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
    this.amount = amount;
  }

  public FixedAmountVoucher(VoucherDto voucherDto) {
    this(voucherDto.voucherId(), voucherDto.customerId(), voucherDto.amount());
  }

  public long discount(long beforeDiscount) {
    return beforeDiscount - amount;
  }

  @Override
  public UUID getVoucherId() {
    return voucherId;
  }

  @Override
  public Long getAmount() {
    return amount;
  }

  @Override
  public int getType() {
    return VoucherType.FIXED.getCode();
  }

  @Override
  public UUID getCustomerId() {
    return customerId;
  }

  @Override
  public void assign(UUID customerId) {
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    return MessageFormat.format("Voucher: [voucherId={0}, amount={1}, type={2}, customerId={3}]",
        voucherId,
        amount,
        VoucherType.FIXED.name(),
        customerId);
  }
}