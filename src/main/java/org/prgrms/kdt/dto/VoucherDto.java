package org.prgrms.kdt.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.type.VoucherType;

public record VoucherDto(
    UUID voucherId,
    UUID customerId,
    Long amount,
    VoucherType voucherType,
    LocalDateTime createdAt) {

  public static VoucherDto of(VoucherRequest.CreateRequest voucherRequest) {
    return new VoucherDto(
        UUID.randomUUID(),
        UUID.randomUUID(),
        voucherRequest.amount(),
        VoucherType.of(voucherRequest.type()),
        LocalDateTime.now());
  }
}