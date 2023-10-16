package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.console.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateVoucherDto {
    private static final Long MIN_DISCOUNT_PERCENT = 0L;
    private static final Long MAX_DISCOUNT_PERCENT = 0L;

    private final VoucherType voucherType;
    private final Long discountAmount;

    public boolean isValidPercent() {
        return (discountAmount >= MIN_DISCOUNT_PERCENT) && (discountAmount <= MAX_DISCOUNT_PERCENT);
    }
}
