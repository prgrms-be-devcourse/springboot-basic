package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface Voucher {

  UUID getVoucherId();

  String getType();

  long discount(long beforeDiscount);

  enum Type {
    FIXED_AMOUNT("1", FixedAmountVoucher::from, "$"), PERCENT_DISCOUNT("2",
        PercentDiscountVoucher::from, "%");

    private final String id;
    private final Function<Long, Voucher> factory;
    private final String unit;


    Type(String id, Function<Long, Voucher> factory, String unit) {
      this.id = id;
      this.factory = factory;
      this.unit = unit;
    }

    public static Optional<Type> from(String candidate) {
      for (var type : Type.values()) {
        if (type.id.equals(candidate)) {
          return Optional.of(type);
        }
      }

      return Optional.empty();
    }

    public Voucher createVoucher(long amount) {
      return factory.apply(amount);
    }

    public String getUnit() {
      return unit;
    }
  }


}
