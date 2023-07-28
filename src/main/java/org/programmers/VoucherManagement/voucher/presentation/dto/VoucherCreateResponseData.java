package org.programmers.VoucherManagement.voucher.presentation.dto;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public record VoucherCreateResponseData(
        String voucherId,
        DiscountType discountType,
        int discountValue
) {
}
