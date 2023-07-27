package org.programmers.VoucherManagement.voucher.presentation.dto;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

import java.util.UUID;

public record VoucherCreateResponseData(
        UUID voucherId,
        DiscountType discountType,
        int discountValue
) {
}
