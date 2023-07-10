package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;

import java.util.UUID;

public record VoucherUpdateRequest(UUID id, DiscountType type, int amount) {
}
