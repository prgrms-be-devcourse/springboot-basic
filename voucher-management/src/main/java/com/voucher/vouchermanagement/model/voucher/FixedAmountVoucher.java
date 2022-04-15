package com.voucher.vouchermanagement.model.voucher;

import com.voucher.vouchermanagement.service.CreateVoucherDto;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

  private final UUID id;
  private Long value;
  private final LocalDateTime createdAt;

  public FixedAmountVoucher(UUID id, Long value, LocalDateTime createdAt) {
    this.id = id;
    this.value = value;
    this.createdAt = createdAt;
  }

  public static Voucher createVoucher(CreateVoucherDto createVoucherDto) {
    return new FixedAmountVoucher(createVoucherDto.getId(),
        createVoucherDto.getValue(),
        createVoucherDto.getCreatedAt());
  }

  @Override
  public Long discount(Long beforeDiscount) {
    return beforeDiscount - value;
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
    return "voucher id = " + id + ", amount = " + value +", createdAt = " + createdAt;
  }
}
