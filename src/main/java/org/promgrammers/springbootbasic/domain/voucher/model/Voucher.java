package org.promgrammers.springbootbasic.domain.voucher.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class Voucher {

    private final UUID voucherId;

    private long amount;

    public UUID getVoucherId() {
        return voucherId;
    }

    protected Voucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public boolean isPercentVoucher() {
        return getVoucherType() == VoucherType.PERCENT;
    }

    public boolean isFixedAmountVoucher() {
        return getVoucherType() == VoucherType.FIXED;
    }

    public void updateAmount(long newAmount) {
        validateAmount(newAmount);
        this.amount = newAmount;
    }

    protected abstract void validateAmount(long amount);

    public abstract VoucherType getVoucherType();

    public abstract long discount(long beforeDiscount);

}
