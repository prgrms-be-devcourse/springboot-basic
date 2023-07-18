package org.programmers.VoucherManagement.voucher.dto.request;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;

public record VoucherCreateRequest(
        DiscountType discountType,
        int discountValue
) {
}
