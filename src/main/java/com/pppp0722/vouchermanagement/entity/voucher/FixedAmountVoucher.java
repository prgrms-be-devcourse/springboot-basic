package com.pppp0722.vouchermanagement.entity.voucher;

import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType type = VoucherType.FIXED_AMOUNT;
    private final UUID memberId;

    public FixedAmountVoucher(UUID voucherId, long amount, UUID memberId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.memberId = memberId;
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
    public UUID getMemberId() {
        return memberId;
    }

    @Override
    public VoucherType getType() {
        return type;
    }
}
