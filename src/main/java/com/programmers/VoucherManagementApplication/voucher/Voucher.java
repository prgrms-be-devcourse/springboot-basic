package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Amount amount;

    public Voucher(UUID voucherId, VoucherType voucherType, Amount amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Amount getAmount() {
        return amount;
    }

    //abstract long discount();

    @Override
    public String toString() {
        return "voucherId = " + voucherId +
                ", voucherType = " + voucherType +
                ", amount = " + amount.getAmount();
    }
}
