package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

@Getter
public class VoucherDto {

  private final UUID voucherId;
  private final String type;
  private final String unit;
  private final LocalDateTime createdAt;

  private final long discountDegree;

  private VoucherDto(UUID voucherId, String type, String unit, long discountDegree, LocalDateTime createdAt) {
    this.voucherId = voucherId;
    this.type = type;
    this.unit = unit;
    this.discountDegree = discountDegree;
    this.createdAt = createdAt;
  }

  public static VoucherDto from(Voucher voucher) {
    return new VoucherDto(voucher.getVoucherId(), voucher.getClass().getSimpleName(), VoucherType.mapToUnit(voucher),
        voucher.getDiscountDegree(), voucher.getCreatedAt());
  }

}


