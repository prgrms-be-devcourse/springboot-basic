package com.programmers.springbootbasic.domain.voucher.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class VoucherRequestDto {
    private final UUID voucherId;
    private final int voucherType;
    private final long value;
}
