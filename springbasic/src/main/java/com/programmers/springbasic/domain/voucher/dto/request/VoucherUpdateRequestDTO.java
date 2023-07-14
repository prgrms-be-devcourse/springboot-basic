package com.programmers.springbasic.domain.voucher.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoucherUpdateRequestDTO {
    private String voucherCode;
    private double value;
}
