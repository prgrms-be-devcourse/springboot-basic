package com.example.commandlineapplication.domain.voucher.dto.mapper;

import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.model.Voucher;
import com.example.commandlineapplication.domain.voucher.model.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VoucherMapper {

  public VoucherResponse voucherToResponse(Voucher voucher) {
    return VoucherResponse.builder()
        .voucherId(voucher.getVoucherId())
        .discountAmount(voucher.getDiscount())
        .voucherType(voucher.getVoucherType())
        .build();
  }

  public VoucherCreateRequest toCreateRequest(VoucherType voucherType, long discount) {
    return VoucherCreateRequest.builder()
        .discountAmount(discount)
        .voucherType(voucherType)
        .build();
  }
}
