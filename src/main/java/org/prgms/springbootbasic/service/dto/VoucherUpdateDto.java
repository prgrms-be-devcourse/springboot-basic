package org.prgms.springbootbasic.service.dto;

import org.prgms.springbootbasic.domain.VoucherType;

import java.util.UUID;

public record VoucherUpdateDto(UUID voucherId,
                               VoucherType voucherType,
                               long discountDegree) {
}
