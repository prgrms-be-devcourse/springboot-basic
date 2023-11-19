package org.prgms.springbootbasic.service.dto;

import org.prgms.springbootbasic.domain.VoucherType;

public record VoucherCreateRequestDto(VoucherType voucherPolicy, long discountDegree) {
}
