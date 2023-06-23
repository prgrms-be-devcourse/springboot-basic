package com.prgrms.commandLineApplication.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
  FIXED("fixed", (voucherId, discountValue) -> new FixedAmountVoucher(voucherId, discountValue)),
  PERCENT("percent", (voucherId, discountValue) -> new PercentDiscountVoucher(voucherId, discountValue));

  private final String type;
  private final BiFunction<UUID, Long, Voucher> createVoucher;
  private static final Map<String, VoucherType> voucherTypeMap = Collections.unmodifiableMap(Stream.of(values())
          .collect(Collectors.toMap(VoucherType::getType, Function.identity())));

  VoucherType(String type, BiFunction<UUID, Long, Voucher> createVoucher) {
    this.type = type;
    this.createVoucher = createVoucher;
  }

  public String getType() {
    return type;
  }

  public Voucher create(UUID voucherId, Long discountValue) {
    return createVoucher.apply(voucherId, discountValue);
  }
}
