package com.programmers.voucher.controller.dto;

import java.util.UUID;

public record VoucherUpdateRequest(UUID voucherId, long discountValue) {
}
