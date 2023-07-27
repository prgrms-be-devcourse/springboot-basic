package org.programmers.VoucherManagement.voucher.presentation.dto;

public record VoucherCreateRequestData(
        String discountType,
        int discountValue
) {
}
