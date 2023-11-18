package org.prgms.springbootbasic.service.dto;

import java.util.UUID;

public record VoucherRequestDto (UUID voucherId, String voucherPolicy, long discountDegree) {
}
