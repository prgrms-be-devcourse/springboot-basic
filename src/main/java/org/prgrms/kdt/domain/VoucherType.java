package org.prgrms.kdt.domain;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
  FIXED(1, "amount", FixedAmountVoucher::new),
  PERCENT(2, "percent", PercentDiscountVoucher::new);

  private final int code;
  private final String measurement;
  private final Function<Long, Voucher> voucherFactory;

  VoucherType(int code, String measurement,
      Function<Long, Voucher> voucherFactory) {
    this.code = code;
    this.measurement = measurement;
    this.voucherFactory = voucherFactory;
  }

  public static VoucherType of(int code) {
    var type = Arrays.stream(values())
        .filter(voucherType -> voucherType.getCode() == code).findFirst();
    return type.orElseThrow(() -> new IllegalArgumentException("Invalid Voucher Type"));
  }

  public int getCode() {
    return code;
  }

  public String getMeasurement() {
    return measurement;
  }

  public Voucher create(Long amount) {
    return voucherFactory.apply(amount);
  }
}