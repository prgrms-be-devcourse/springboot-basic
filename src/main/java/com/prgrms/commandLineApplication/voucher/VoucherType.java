package com.prgrms.commandLineApplication.voucher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum VoucherType {
  FIXED,
  PERCENT;

  private static final String ERROR_MESSAGE = "Invalid Voucher Type";
  private static final Map<String, VoucherType> VOUCHER_TYPES = new HashMap<>();

  static {
    Arrays.stream(values())
            .forEach(voucher -> VOUCHER_TYPES.put(voucher.name(), voucher));
  }

  public static VoucherType valueOfType(String type) {
    System.out.println(ERROR_MESSAGE + " -> " + type);
    return Optional.ofNullable(VOUCHER_TYPES.get(type))
            .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE + " -> " + type));
  }

}
