package org.prgms.voucher;

import com.google.common.base.Preconditions;

import java.util.UUID;

public record PercentDiscountVoucher(UUID voucherId, long discountAmount) implements Voucher {

    public PercentDiscountVoucher {
        Preconditions.checkArgument(voucherId != null, "바우처 아이디는 null 일 수 없습니다.");
    }

    @Override
    public long apply(long beforeDiscount) {
        return (long) ((1 - (discountAmount / 100.0)) * beforeDiscount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }
}
