package org.prgms.springbootbasic.domain.voucher;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected VoucherType voucherType;
    protected long amount;
    protected UUID customerId;

     Voucher(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    protected Voucher(UUID voucherId, VoucherType voucherType, long amount, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
        this.customerId = customerId;
    }

    public abstract long discount(long beforeAmount);

    public abstract boolean isValidAmount(long amount);


    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}
