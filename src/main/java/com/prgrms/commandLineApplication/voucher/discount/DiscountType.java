package com.prgrms.commandLineApplication.voucher.discount;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum DiscountType {
  FIXED,
  PERCENT;

  private static final String ERROR_MESSAGE = "Invalid Voucher Type";
  private static final Map<String, DiscountType> VOUCHER_TYPES = new HashMap<>();

  static {
    Arrays.stream(values())
            .forEach(voucher -> VOUCHER_TYPES.put(voucher.name(), voucher));
  }

  public static DiscountType valueOfType(String type) {
    return Optional.ofNullable(VOUCHER_TYPES.get(type.toUpperCase()))
            .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE + " -> " + type));
  }

}
