package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import jakarta.validation.constraints.Min;

public record VoucherUpdateRequest(DiscountType type, @Min(1) int amount) {
}
