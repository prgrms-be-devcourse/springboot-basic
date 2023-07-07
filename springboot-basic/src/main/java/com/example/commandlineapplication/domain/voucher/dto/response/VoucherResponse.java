package com.example.commandlineapplication.domain.voucher.dto.response;

import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class VoucherResponse {

  private final UUID voucherId;
  private final VoucherType voucherType;
  private final long discountAmount;
}
