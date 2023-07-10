package me.kimihiqq.vouchermanagement.option;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;

import java.util.UUID;
import java.util.function.Function;

public enum VoucherTypeOption implements ConsoleOption {
    FIXED(1, "Fixed amount voucher", discount -> new FixedAmountVoucher(UUID.randomUUID(), discount)),
    PERCENT(2, "Percent discount voucher", discount -> new PercentDiscountVoucher(UUID.randomUUID(), discount));

    private final int key;
    private final String description;
    private final Function<Long, Voucher> createVoucherFunction;

    VoucherTypeOption(int key, String description, Function<Long, Voucher> createVoucherFunction) {
        this.key = key;
        this.description = description;
        this.createVoucherFunction = createVoucherFunction;
    }

    public Voucher createVoucher(long discount) {
        return this.createVoucherFunction.apply(discount);
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}