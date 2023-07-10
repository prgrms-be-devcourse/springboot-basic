package com.example.commandlineapplication.domain.voucher.dto.mapper;

import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherMapper {

  public VoucherResponse voucherToResponse(Voucher voucher) {
    return VoucherResponse.builder()
        .voucherId(UUID.fromString(voucher.getVoucherId().toString()))
        .discountAmount(voucher.getDiscount())
        .voucherType(voucher.getVoucherType())
        .build();
  }

  public VoucherCreateRequest toCreateRequest(UUID voucherId, VoucherType voucherType,
      long discount) {
    return VoucherCreateRequest.builder()
        .voucherId(voucherId)
        .discountAmount(discount)
        .voucherType(voucherType)
        .build();
  }
}




