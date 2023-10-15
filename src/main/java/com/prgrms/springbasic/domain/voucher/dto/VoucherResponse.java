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

    @Override
    public String toString() {
        return """
                Voucher Id : %s
                Discount Type : %s
                Discount Value : %d
                ------------------------------
                """.formatted(voucherId, discountType, discountValue);
    }
}
