package org.programmers.VoucherManagement.voucher.application.dto;

public record VoucherCreateRequest(
        String discountType,
        int discountValue
) {
}
