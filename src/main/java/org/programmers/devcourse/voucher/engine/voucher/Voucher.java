package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Voucher {

  UUID getVoucherId();


  long discount(long beforeDiscount);

  enum Type {
    FIXED_AMOUNT("1", FixedAmountVoucher.class, "$"), PERCENT_DISCOUNT("2",
        PercentDiscountVoucher.class, "%");

    private static final Map<String, Type> mapper = Collections.unmodifiableMap(
        Stream.of(values()).collect(Collectors.toMap(value -> value.id, value -> value)));
    private final String id;
    private final Class<? extends Voucher> voucherClass;
    private final String unit;


    Type(String id, Class<? extends Voucher> voucherClass, String unit) {
      this.id = id;
      this.voucherClass = voucherClass;
      this.unit = unit;
    }

    public static Optional<Type> from(String candidate) {

      return Optional.ofNullable(mapper.get(candidate));
    }


    public String getUnit() {
      return unit;
    }

    public Class<? extends Voucher> getVoucherClass() {
      return voucherClass;
    }
  }


}
