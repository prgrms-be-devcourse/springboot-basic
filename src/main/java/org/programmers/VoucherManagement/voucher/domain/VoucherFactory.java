package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;

import java.util.UUID;


public class VoucherFactory {
    private VoucherFactory() {
    }

    public static Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        DiscountType discountType = DiscountType.from(voucherCreateRequest.discountType());

        return switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(voucherCreateRequest.discountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(voucherCreateRequest.discountValue()));
        };
    }

    public static Voucher mapVoucher(UUID voucherId, int discountValue, DiscountType discountType) {
        return switch (discountType) {
            case FIXED -> new FixedAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
            case PERCENT -> new PercentAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
        };
    }
}
