package com.programmers.vouchermanagement.voucher.dto.request;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record VoucherUpdateRequest(UUID id, DiscountType type, @Min(1) int amount) {
}
