package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;

public record VoucherCreationRequest(DiscountType type, int amount) {
}