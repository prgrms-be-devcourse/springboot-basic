package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.enums.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID customerId;
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID customerId, UUID voucherId, long amount, VoucherType voucherType) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));

        this.customerId = customerId;
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - amount;
        return (discountAmount < 0) ? 0 : discountAmount;
    }

    @Override
    public VoucherType getVoucherType(){ return VoucherType.FIXED; };

    @Override
    public String toString() {
        return MessageFormat.format("Fixed Amount Voucher [ customerId = {0}, voucherId = {1}, amount = {2} ]", customerId, voucherId, amount);
    }

}