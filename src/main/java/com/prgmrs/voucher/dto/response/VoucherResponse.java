package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.strategy.DiscountStrategy;

import java.util.UUID;

public record VoucherResponse(UUID voucherUuid, DiscountStrategy discountStrategy) {
}
