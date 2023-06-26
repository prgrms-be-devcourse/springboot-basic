package com.example.commandlineapplication.domain.voucher.model;

import java.util.Arrays;

public enum VoucherType {

  FIXED,
  PERCENT;

  public static VoucherType of(String inputVoucherType) {
    return Arrays.stream(values())
        .filter(voucher -> voucher.isEquals(inputVoucherType, voucher))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
  }

  private boolean isEquals(String inputVoucherType, VoucherType voucher) {
    return voucher.getLowerCaseVoucherType().equals(inputVoucherType);
  }

  public String getLowerCaseVoucherType() {
    return this.name().toLowerCase();
  }
}
