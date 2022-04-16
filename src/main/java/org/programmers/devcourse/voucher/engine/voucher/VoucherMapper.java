package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.FixedAmountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

public enum VoucherMapper {
  FIXED_AMOUNT("1",
      "$", FixedAmountVoucher.factory),
  PERCENT_DISCOUNT("2",
      "%", PercentDiscountVoucher.factory);

  private static final Map<String, VoucherMapper> idToMapperStorage = Collections.unmodifiableMap(
      Stream.of(VoucherMapper.values())
          .collect(Collectors.toMap(value -> value.id, value -> value)));


  private final String id;
  private final String unit;
  private final VoucherFactory factory;


  VoucherMapper(String id, String unit,
      VoucherFactory factory) {
    this.id = id;
    this.unit = unit;
    this.factory = factory;
  }

  public static Optional<VoucherMapper> from(String candidate) {

    return Optional.ofNullable(idToMapperStorage.get(candidate));
  }

  public static String mapToId(Voucher voucher) {
    // 바우처의 타입을 확인한다.
    if (FixedAmountVoucher.class.equals(voucher.getClass())) {
      return FIXED_AMOUNT.id;
    } else if (PercentDiscountVoucher.class.equals(voucher.getClass())) {
      return PERCENT_DISCOUNT.id;
    }
    throw new VoucherException("No corresponding voucher type");
  }


  public String getUnit() {
    return unit;
  }


  public VoucherFactory getFactory() {
    return factory;
  }
}
