package org.prgms.springbootbasic.service.dto;

import org.prgms.springbootbasic.domain.VoucherType;

import java.time.LocalDateTime;

public record VoucherFilterDto(LocalDateTime startDay,
                               LocalDateTime endDay,
                               VoucherType voucherType) {
}
