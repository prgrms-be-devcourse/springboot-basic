package com.programmers.springbasic.domain.voucher.dto.request;

import com.programmers.springbasic.domain.voucher.model.VoucherType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherCreateRequestDTO {
    private String customerId;
    private VoucherType voucherType;
    private double voucherValue;
}
