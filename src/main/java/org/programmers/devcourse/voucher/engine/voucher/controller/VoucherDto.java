package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.util.UUID;
import lombok.Getter;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

@Getter
public class VoucherDto {

  private final UUID voucherId;
  private final String type;
  private final String unit;

  private final long discountDegree;

  private VoucherDto(UUID voucherId, String type, String unit, long discountDegree) {
    this.voucherId = voucherId;
    this.type = type;
    this.unit = unit;
    this.discountDegree = discountDegree;
  }

  public static VoucherDto from(Voucher voucher) {
    return new VoucherDto(voucher.getVoucherId(), voucher.getClass().getSimpleName(), VoucherType.mapToUnit(voucher), voucher.getDiscountDegree());
  }

}


