package me.programmers.springboot.basic.springbootbasic.voucher.model;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherUpdateRequestDto;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("음수 값으로 할인 할 수 없습니다.");
        }
        if (amount == 0) {
            throw new IllegalArgumentException("0원으로 할인 할 수 없습니다.");
        }
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public void update(VoucherUpdateRequestDto requestDto) {
        this.amount = Long.parseLong(requestDto.getDiscountPrice());
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", amount=" + amount +
                '}';
    }
}
