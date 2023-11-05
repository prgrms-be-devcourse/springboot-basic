package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public record Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
}
