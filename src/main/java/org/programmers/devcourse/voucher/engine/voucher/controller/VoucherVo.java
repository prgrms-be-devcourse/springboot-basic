package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.util.UUID;
import lombok.Getter;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

// Voucher Template 정보 전달 용

@Getter
public class VoucherVo {

  private final UUID id;
  private final String type;
  private final String unit;

  private final long discountDegree;

  private VoucherVo(UUID id, String type, String unit, long discountDegree) {
    this.id = id;
    this.type = type;
    this.unit = unit;
    this.discountDegree = discountDegree;

    // TODO: 브라우저에서 볼 수 있는 형태로 출력할 수 있는 Vo만들기
  }

  public static VoucherVo from(Voucher voucher) {

    return new VoucherVo(voucher.getVoucherId(), voucher.getClass().getSimpleName(),
        VoucherType.mapToUnit(voucher), voucher.getDiscountDegree());
  }

}


