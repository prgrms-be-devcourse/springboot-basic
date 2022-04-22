package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.FixedAmountException;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int amount;

    public FixedAmountVoucher(UUID voucherId, int amount) throws FixedAmountException{
        this.voucherId = voucherId;
        if(amount <= 0)
            throw new FixedAmountException("고정 금액 할인은 0원보다 크게 입력해야합니다.\n");
        this.amount = amount;
        this.voucherType = VoucherType.FIXED;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int discount(int beforeDiscount) {
        int discountAmount = beforeDiscount - amount;
        return discountAmount < 0 ? 0 : discountAmount;
    }

    @Override
    public String voucherTypeToString() {
        return this.voucherType.getTypeString();
    }


    @Override
    public String toString() {
        return String.format("%-20s %-6d", "FixedAmountVoucher", this.amount);
    }
}
