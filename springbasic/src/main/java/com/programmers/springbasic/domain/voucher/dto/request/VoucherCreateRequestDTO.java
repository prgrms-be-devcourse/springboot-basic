package com.programmers.springbasic.domain.voucher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherCreateRequestDTO {
    private String customerId;
    private String voucherType;
    private double voucherValue;
}
