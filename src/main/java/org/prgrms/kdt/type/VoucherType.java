package org.prgrms.kdt.type;

import java.util.Arrays;
import java.util.function.Function;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;

public enum VoucherType {
  FIXED(1, "amount", FixedAmountVoucher::new),
  PERCENT(2, "percent", PercentDiscountVoucher::new);

  private final int code;
  private final String measurement;
  private final Function<VoucherDto, Voucher> voucherFactory;

  VoucherType(int code,
      String measurement,
      Function<VoucherDto, Voucher> voucherFactory) {
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

  public Voucher create(VoucherDto voucherDto) {
    return voucherFactory.apply(voucherDto);
  }
}