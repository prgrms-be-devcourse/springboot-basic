package org.programmers.devcourse.voucher.engine.voucher.controller;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.Getter;
import org.programmers.devcourse.voucher.engine.voucher.VoucherType;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

@Getter
@XStreamAlias("Voucher")
public class VoucherDto {

  private final UUID voucherId;
  private final String type;
  private final String unit;
  private final String createdAt;
  private final long discountDegree;

  private VoucherDto(UUID voucherId, String type, String unit, long discountDegree, LocalDateTime createdAt) {
    this.voucherId = voucherId;
    this.type = type;
    this.unit = unit;
    this.discountDegree = discountDegree;
    // TODO: DTO의 책임 범위에 대해서, 웹 페이지에 출력만 담당하는 DTO일 경우 꼭 LocalDateTime 형태로 보관해야 하는 걸까요?
    this.createdAt = createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public static VoucherDto from(Voucher voucher) {
    return new VoucherDto(voucher.getVoucherId(), voucher.getClass().getSimpleName(), VoucherType.mapToUnit(voucher),
        voucher.getDiscountDegree(), voucher.getCreatedAt());
  }

}


