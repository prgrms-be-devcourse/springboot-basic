package com.programmers.voucher.controller.voucher.dto;

import java.util.UUID;

public record VoucherUpdateRequest(UUID voucherId, long discountValue) {
}
