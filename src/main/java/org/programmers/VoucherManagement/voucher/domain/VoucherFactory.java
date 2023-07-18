package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.dto.request.VoucherCreateRequest;

import java.util.UUID;


public class VoucherFactory {
    private VoucherFactory() {
    }

    public static Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        DiscountType discountType = voucherCreateRequest.getDiscountType();

        return switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(voucherCreateRequest.getDiscountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(voucherCreateRequest.getDiscountValue()));
        };
    }

    public static Voucher mapVoucher(UUID voucherId, int discountValue, DiscountType discountType) {
        return switch (discountType) {
            case FIXED -> new FixedAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
            case PERCENT -> new PercentAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
        };
    }
}
