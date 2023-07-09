package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public static Voucher createVoucher(CreateVoucherRequest createVoucherRequest) {
        DiscountType discountType = createVoucherRequest.getDiscountType();

        Voucher voucher = switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
        };

        return voucher;
    }

    public static Voucher mapVoucher(UUID voucherId, int discountValue, DiscountType discountType) {
        Voucher voucher = switch (discountType) {
            case FIXED -> new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(discountValue));
            case PERCENT -> new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(discountValue));
        };
        return voucher;
    }
}
