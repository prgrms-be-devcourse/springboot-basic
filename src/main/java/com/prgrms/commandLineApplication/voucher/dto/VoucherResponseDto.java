package com.prgrms.commandLineApplication.voucher.dto;

import com.prgrms.commandLineApplication.voucher.discount.DiscountType;

import java.util.UUID;

public record VoucherResponseDto(UUID voucherId, DiscountType discountType, int discountAmount) {
}
