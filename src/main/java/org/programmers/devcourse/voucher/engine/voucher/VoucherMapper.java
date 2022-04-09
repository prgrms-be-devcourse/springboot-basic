package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherMapper {
  FIXED_AMOUNT("1",
      FixedAmountVoucher.class, "$", FixedAmountVoucher.factory),
  PERCENT_DISCOUNT("2",
      PercentDiscountVoucher.class, "%", PercentDiscountVoucher.factory);

  private static final Map<String, VoucherMapper> idToMapperStorage = Collections.unmodifiableMap(
      Stream.of(VoucherMapper.values())
          .collect(Collectors.toMap(value -> value.id, value -> value)));


  private final String id;
  private final Class<? extends Voucher> voucherClass;
  private final String unit;
  private final VoucherFactory factory;


  VoucherMapper(String id, Class<? extends Voucher> voucherClass, String unit,
      VoucherFactory factory) {
    this.id = id;
    this.voucherClass = voucherClass;
    this.unit = unit;
    this.factory = factory;
  }

  public static Optional<VoucherMapper> from(String candidate) {

    return Optional.ofNullable(idToMapperStorage.get(candidate));
  }

  public static Optional<VoucherMapper> fromSimpleClassName(String className) {

    for (VoucherMapper voucherMapper : VoucherMapper.values()) {
      if (voucherMapper.voucherClass.getSimpleName().equals(className)) {
        return Optional.of(voucherMapper);
      }
    }
    return Optional.empty();
  }


  public String getUnit() {
    return unit;
  }


  public VoucherFactory getFactory() {
    return factory;
  }
}
