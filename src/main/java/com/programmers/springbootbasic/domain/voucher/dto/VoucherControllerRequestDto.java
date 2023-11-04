package com.programmers.springbootbasic.domain.voucher.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherControllerRequestDto {
    private final String voucherId;
    private final long value;
    private final int voucherType;
}
