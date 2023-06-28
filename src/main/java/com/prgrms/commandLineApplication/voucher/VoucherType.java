package com.prgrms.commandLineApplication.voucher;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum VoucherType {
  FIXED,
  PERCENT;

  private static final String ERROR_MESSAGE = "Invalid Voucher Type";
  private static final Map<String, VoucherType> voucherTypes = new ConcurrentHashMap<>();

  static {
    Arrays.stream(values())
            .forEach(voucher -> voucherTypes.put(voucher.name(), voucher));
  }

  public static VoucherType valueOfType(String type) {
    return Optional.ofNullable(voucherTypes.get(type))
            .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
  }

}
