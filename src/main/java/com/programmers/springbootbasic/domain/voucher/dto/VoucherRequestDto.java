package com.programmers.springbootbasic.domain.voucher.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoucherRequestDto {
    private final int voucherType;
    private final long value;
}
