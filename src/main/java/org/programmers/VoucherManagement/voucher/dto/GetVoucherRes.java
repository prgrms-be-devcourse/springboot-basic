package org.programmers.VoucherManagement.voucher.dto;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

public class GetVoucherRes {
    private final UUID voucherId;
    private final DiscountType discountType;
    private final int discountValue;

    public GetVoucherRes(UUID voucherId, DiscountType discountType, int discountValue) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public static GetVoucherRes toDto(Voucher voucher) {
        return new GetVoucherRes(voucher.getVoucherId(), voucher.getDiscountType(), voucher.getDiscountValue());
    }


}
