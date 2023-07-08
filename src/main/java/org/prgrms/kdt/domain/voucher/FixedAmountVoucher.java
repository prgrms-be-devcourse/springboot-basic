package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final Long voucherId;
    private final long amount;
    private boolean status = false;


    public FixedAmountVoucher(Long voucherId, long amount) {
        validate(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }


    private static void validate(long amount) {
        if (amount < 0) throw new IllegalArgumentException("할인 금액은 양수여야 합니다.");
        if (amount == 0) throw new IllegalArgumentException("할인 금액은 0원이 될 수 없습니다.");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("할인 최대 금액 %d원을 넘어섰습니다.".formatted(MAX_VOUCHER_AMOUNT));
    }

    @Override
    public Long getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public Long getDiscountAmount() {
        return amount;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

}
