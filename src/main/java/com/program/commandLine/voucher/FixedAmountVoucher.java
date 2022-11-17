package com.program.commandLine.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_VOUCHER_AMOUNT = 100000;
    private static final String VOUCHER_TYPE = "Fixed_amount";
    private final UUID voucherId;
    private final int amount;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        if (amount < 0) throw new IllegalArgumentException("! Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("! Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("! Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
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
    public int discountPrice(int beforeDiscount) {
        return Math.max(beforeDiscount - amount, 0);
    }
}
