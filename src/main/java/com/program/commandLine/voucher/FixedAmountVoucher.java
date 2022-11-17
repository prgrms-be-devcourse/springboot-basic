package com.program.commandLine.voucher;

import java.util.Optional;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_VOUCHER_AMOUNT = 100000;

    private final UUID voucherId;
    private final int amount;
    private UUID assignedCustomerId;
    private boolean using = false;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("! Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("! Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("! Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, int amount, UUID assignedCustomerId, boolean using) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.assignedCustomerId = assignedCustomerId;
        this.using = using;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED_AMOUNT_DISCOUNT;
    }

    @Override
    public int getVoucherDiscount() {
        return amount;
    }

    @Override
    public UUID getAssignedCustomerId() {
        return assignedCustomerId;
    }

    @Override
    public boolean getUsing() {
        return using;
    }

    @Override
    public int discountPrice(int beforeDiscount) {
        return Math.max(beforeDiscount - amount, 0);
    }
}
