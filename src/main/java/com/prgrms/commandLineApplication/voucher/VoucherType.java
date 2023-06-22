package com.prgrms.commandLineApplication.voucher;

import java.util.Arrays;

public enum VoucherType {
  FIXED("fixed"),
  PERCENT("percent");

  private final String type;

  VoucherType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void checkType(String voucherType) {
    Arrays.stream(VoucherType.values())
            .filter(voucher -> voucher.getType().equals(voucherType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Type " + voucherType + " does not exist. \n Please select the type in the view."));
  }
}
