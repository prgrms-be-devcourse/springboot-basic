package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;

import java.util.UUID;


public class VoucherFactory {
    public static Voucher createVoucher(CreateVoucherRequest createVoucherRequest) {
        DiscountType discountType = createVoucherRequest.getDiscountType();

        return switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
        };
    }

    public static Voucher mapVoucher(UUID voucherId, int discountValue, DiscountType discountType) {
        return switch (discountType) {
            case FIXED -> new FixedAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
            case PERCENT -> new PercentAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
        };
    }
}
