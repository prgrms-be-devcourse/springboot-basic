package org.programmers.VoucherManagement.voucher.domain;

import com.github.f4b6a3.ulid.UlidCreator;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;


public class VoucherFactory {
    private VoucherFactory() {
    }

    public static Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        DiscountType discountType = DiscountType.from(voucherCreateRequest.discountType());

        return switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UlidCreator.getUlid().toString(), discountType, new DiscountValue(voucherCreateRequest.discountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UlidCreator.getUlid().toString(), discountType, new DiscountValue(voucherCreateRequest.discountValue()));
        };
    }

    public static Voucher mapVoucher(String voucherId, int discountValue, DiscountType discountType) {
        return switch (discountType) {
            case FIXED -> new FixedAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
            case PERCENT -> new PercentAmountVoucher(voucherId, discountType, new DiscountValue(discountValue));
        };
    }
}
