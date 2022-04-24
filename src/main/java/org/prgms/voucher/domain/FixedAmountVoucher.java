package org.prgms.voucher.domain;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public record FixedAmountVoucher(

        /* 바우처 아이디 */
        UUID voucherId,

        /* 할인 양 */
        long discountAmount

) implements Voucher {

    public FixedAmountVoucher {
        checkArgument(voucherId != null, "바우처 아이디는 null 일 수 없습니다.");
    }

    public FixedAmountVoucher(long discountAmount) {
        this(UUID.randomUUID(), discountAmount);
    }

    @Override
    public long apply(long beforeDiscount) {
        return beforeDiscount - discountAmount;
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
