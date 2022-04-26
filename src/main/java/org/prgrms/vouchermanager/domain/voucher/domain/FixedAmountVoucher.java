package org.prgrms.vouchermanager.domain.voucher.domain;

import lombok.ToString;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@ToString(callSuper = true)
public class FixedAmountVoucher extends AbstractVoucher {

    /* amount 최댓값 */
    public static final long MAX_FIXED_VOUCHER_AMOUNT = 10000;
    
    /* 고정 할인 금액 */
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, VoucherType.FIXED);

        checkArgument(amount > 0 && amount <= MAX_FIXED_VOUCHER_AMOUNT, "Amount should be between 0 and %d".formatted(MAX_FIXED_VOUCHER_AMOUNT));

        this.amount = amount;
    }

    @Override
    public long getDiscountValue() {
        return this.amount;
    }

    @Override
    public Long discount(long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }
}
