package org.promgrammers.springbootbasic.domain.voucher.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class Voucher {

    private final UUID voucherId;
    private long amount;
    private UUID customerId;

    public UUID getVoucherId() {
        return voucherId;
    }

    protected Voucher(UUID voucherId, long amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public void updateAmount(long newAmount) {
        validateAmount(newAmount);
        this.amount = newAmount;
    }

    public Voucher assignCustomerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    protected abstract void validateAmount(long amount);

    public abstract VoucherType getVoucherType();

    public abstract long discount(long beforeDiscount);

}
