package com.voucher.vouchermanagement.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

  private UUID id;
  private Long value;
  private LocalDateTime createdAt;
  private final VoucherType vouchertype = VoucherType.PercentDiscountVoucher;
  
  public PercentDiscountVoucher(UUID id, Long value, LocalDateTime createdAt) {
    this.id = id;
    this.value = value;
    this.createdAt = createdAt;
  }

  @Override
  public Long discount(Long beforeDiscount) {
    return beforeDiscount * (1 - (value / 100));
  }

  @Override
  public UUID getVoucherId() {
    return id;
  }

  @Override
  public Long getValue() {
    return value;
  }

  @Override
  public VoucherType getVoucherType() {
    return vouchertype;
  }

  @Override
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "voucher id = " + id + ", percent = " + value + "%, createdAt = " + createdAt;
  }
}
