package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.FixedAmountException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, int amount) throws FixedAmountException{
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.FIXED;
        this.createdAt = LocalDateTime.now();
    }

    public FixedAmountVoucher(UUID voucherId, int amount, LocalDateTime createdAt) throws FixedAmountException{
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.FIXED;
        this.createdAt = createdAt;
    }

    @Override
    public int discount(int beforeDiscount) {
        int discountAmount = beforeDiscount - amount;
        return discountAmount < 0 ? 0 : discountAmount;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d", "FixedAmountVoucher", this.amount);
    }

    private void validateAmount(int amount){
        if(amount <= 0)
            throw new FixedAmountException("고정 금액 할인은 0원보다 크게 입력해야합니다.\n");
    }
}
