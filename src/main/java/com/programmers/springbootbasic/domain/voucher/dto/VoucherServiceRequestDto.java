package com.programmers.springbootbasic.domain.voucher.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
public class VoucherServiceRequestDto {
    private final UUID voucherId;
    private final int voucherType;
    private final long value;
    private final LocalDate date;
}
