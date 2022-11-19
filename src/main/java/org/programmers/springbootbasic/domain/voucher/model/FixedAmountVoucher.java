package org.programmers.springbootbasic.domain.voucher.model;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.exception.MinusDiscountResultException;
import org.programmers.springbootbasic.exception.WrongRangeInputException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_FIXED_AMOUNT = Integer.MAX_VALUE;
    private static final int MIN_FIXED_AMOUNT = 0;

    private final UUID voucherId;
    private final long amount;

    private final VoucherType voucherType = VoucherType.FIXED;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        validateAmountRange();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long originalPrice) {
        if (originalPrice - amount < 0) throw new MinusDiscountResultException("할인된 금액은 0원보다 작을 수 없습니다.");
        return originalPrice - amount;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ", " +
                voucherId.toString() + ", " +
                amount;
    }

    private void validateAmountRange() {
        if (!(MIN_FIXED_AMOUNT <= amount && amount <= MAX_FIXED_AMOUNT))
            throw new WrongRangeInputException("올바르지 않은 Amount의 값 범위입니다.");
    }
}
