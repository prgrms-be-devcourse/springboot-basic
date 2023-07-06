package com.example.commandlineapplication.domain.voucher.dto.request;

import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoucherCreateRequest {

  private final VoucherType voucherType;
  private final long discountAmount;

}
