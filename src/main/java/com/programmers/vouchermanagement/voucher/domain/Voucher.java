package com.programmers.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record Voucher(UUID voucherId, long discountValue, VoucherType voucherType, LocalDateTime createdAt) {
}
