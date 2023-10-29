package com.prgrms.springbasic.domain.voucher.dto;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;

import java.util.UUID;

public record VoucherResponse(
        UUID voucherId, DiscountType discountType, long discountValue
) {
    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getDiscountType(),
                voucher.getDiscountValue()
        );
    }
}
