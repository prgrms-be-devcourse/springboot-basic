package com.example.commandlineapplication.domain.voucher.dto.request;

import com.example.commandlineapplication.domain.voucher.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoucherCreateRequest {

  private UUID voucherId;
  private VoucherType voucherType;
  private Long discountAmount;

  public VoucherCreateRequest(VoucherType voucherType, Long discountAmount) {
    this.voucherType = voucherType;
    this.discountAmount = discountAmount;
  }
}
