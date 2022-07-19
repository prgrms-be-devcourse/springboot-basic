package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.PercentErrorException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PercentAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int amount;
    private final LocalDateTime createdAt;

    public PercentAmountVoucher(UUID voucherId, int amount) throws PercentErrorException{
        validatePercent(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.PERCENT;
        this.createdAt = LocalDateTime.now();
    }

    public PercentAmountVoucher(UUID voucherId, int amount, LocalDateTime createdAt){
        validatePercent(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = VoucherType.PERCENT;
        this.createdAt = createdAt;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int discount(int beforeDiscount) {
        return beforeDiscount * (100 - amount) / 100;
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d", "PercentAmountVoucher", this.amount);
    }

    private void validatePercent(int amount){
        if(amount > 100)
            throw new PercentErrorException("비율은 100%를 넘길 수 없습니다.\n");
        else if(amount <= 0)
            throw new PercentErrorException("비율은 0%보다 커야합니다.\n");
    }
}
