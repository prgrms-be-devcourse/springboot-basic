package com.prgrms.commandLineApplication.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
  FIXED("fixed", (voucherId, discountValue) -> new FixedAmountVoucher(voucherId, discountValue)),
  PERCENT("percent", (voucherId, discountValue) -> new PercentDiscountVoucher(voucherId, discountValue));

  private final String type;
  private final BiFunction<UUID, Long, Voucher> createVoucher;

  VoucherType(String type, BiFunction<UUID, Long, Voucher> createVoucher) {
    this.type = type;
    this.createVoucher = createVoucher;
  }

  public String getType() {
    return type;
  }

  public Voucher create(UUID voucherId, Long discountValue, String inputVoucherType) {
    checkType(inputVoucherType);
    return createVoucher.apply(voucherId, discountValue);
  }

  public void checkType(String inputVoucherType) {
    Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.getType().equals(inputVoucherType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Type " + inputVoucherType + " does not exist. \n Please select the type in the view."));
  }
}
