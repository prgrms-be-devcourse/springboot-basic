package com.voucher.vouchermanagement.model.voucher;

import com.voucher.vouchermanagement.service.CreateVoucherDto;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

  private final UUID id;
  private Long value;
  private final LocalDateTime createdAt;

  public PercentDiscountVoucher(UUID id, Long value, LocalDateTime createdAt) {
    this.id = id;
    this.value = value;
    this.createdAt = createdAt;
  }

  public static Voucher createVoucher(CreateVoucherDto createVoucherDto) {
    return new PercentDiscountVoucher(createVoucherDto.getId(),
        createVoucherDto.getValue(),
        createVoucherDto.getCreatedAt());
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
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public String toString() {
    return "voucher id = " + id + ", percent = " + value + "%, createdAt = " + createdAt;
  }
}
