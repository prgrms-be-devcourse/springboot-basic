package org.prgms.springbootbasic.service.dto;

import org.prgms.springbootbasic.domain.VoucherType;

public record VoucherInsertDto(VoucherType voucherType,
                               long discountDegree) {
}
