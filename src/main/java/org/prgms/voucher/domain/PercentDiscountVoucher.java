package org.prgms.voucher.domain;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public record PercentDiscountVoucher(

        /* 바우처 아이디 */
        UUID voucherId,

        /* 할인 퍼센트 */
        long discountAmount

) implements Voucher {

    public PercentDiscountVoucher {
        checkArgument(voucherId != null, "바우처 아이디는 null 일 수 없습니다.");
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
