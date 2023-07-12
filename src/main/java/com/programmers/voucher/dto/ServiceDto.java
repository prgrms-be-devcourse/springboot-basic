package com.programmers.voucher.dto;

import com.programmers.voucher.domain.Discount;

import java.util.UUID;

public record ServiceDto(UUID voucherId, Discount discount) {
}
