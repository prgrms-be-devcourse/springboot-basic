package org.prgms.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherResponseDto(UUID voucherId,
                                 long discountDegree,
                                 String voucherPolicy,
                                 LocalDateTime createdAt) {
}
