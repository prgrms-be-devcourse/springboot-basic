package com.prgrms.voucher.service.dto;

import com.prgrms.voucher.model.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherServiceCreateRequest {

    final VoucherType voucherType;
    final double discountAmount;

}
