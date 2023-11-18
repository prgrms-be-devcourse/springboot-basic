package org.prgms.springbootbasic.controller.voucher.dto;

import java.util.UUID;

public record VoucherRequestDto (UUID voucherId, String voucherPolicy, long discountDegree) {
}
