package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.console.VoucherType;

public record CreateVoucherDto(VoucherType voucherType, Long discountAmount) {
    private static final Long MIN_DISCOUNT_PERCENT = 0L;
    private static final Long MAX_DISCOUNT_PERCENT = 100L;

    public boolean isInvalidPercent() {
        return (discountAmount < MIN_DISCOUNT_PERCENT) || (discountAmount > MAX_DISCOUNT_PERCENT);
    }

    public boolean isInValidPrice() {
        return discountAmount < 0;
    }
}
