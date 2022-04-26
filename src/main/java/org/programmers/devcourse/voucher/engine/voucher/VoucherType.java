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

public enum VoucherType {
  FIXED_AMOUNT("1", "$", FixedAmountVoucher.factory),
  PERCENT_DISCOUNT("2", "%", PercentDiscountVoucher.factory);

  private static final Map<String, VoucherType> idToMapperStorage = Collections.unmodifiableMap(
      Stream.of(VoucherType.values()).collect(Collectors.toMap(value -> value.typeId, value -> value)));

  private final String typeId;
  private final String unit;
  private final VoucherFactory factory;

  VoucherType(String typeId, String unit,
      VoucherFactory factory) {
    this.typeId = typeId;
    this.unit = unit;
    this.factory = factory;
  }

  public static Optional<VoucherType> from(String typeId) {
    return Optional.ofNullable(idToMapperStorage.get(typeId));
  }

  public static String mapToTypeId(Voucher voucher) {
    // 바우처의 타입을 확인한다.
    if (FixedAmountVoucher.class.equals(voucher.getClass())) {
      return FIXED_AMOUNT.typeId;
    } else if (PercentDiscountVoucher.class.equals(voucher.getClass())) {
      return PERCENT_DISCOUNT.typeId;
    }
    throw new VoucherException("No corresponding voucher type");
  }

  public static String mapToUnit(Voucher voucher) {
    // 바우처의 타입을 확인한다.
    if (FixedAmountVoucher.class.equals(voucher.getClass())) {
      return FIXED_AMOUNT.unit;
    } else if (PercentDiscountVoucher.class.equals(voucher.getClass())) {
      return PERCENT_DISCOUNT.unit;
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
