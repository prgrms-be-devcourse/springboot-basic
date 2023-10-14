package com.prgrms.springbasic.dto;

import com.prgrms.springbasic.domain.DiscountType;
import com.prgrms.springbasic.domain.Voucher;

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
