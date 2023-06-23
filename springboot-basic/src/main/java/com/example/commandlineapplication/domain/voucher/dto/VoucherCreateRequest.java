package com.example.commandlineapplication.domain.voucher.dto;

import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoucherCreateRequest {
  private final VoucherType voucherType;
  private final long discountAmount;

  public VoucherType getVoucherType() {
    return voucherType;
  }
  public long getDiscountAmount() {
    return discountAmount;
  }

}
