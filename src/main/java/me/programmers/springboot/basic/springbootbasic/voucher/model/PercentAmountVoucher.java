package me.programmers.springboot.basic.springbootbasic.voucher.model;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherUpdateRequestDto;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {

    private long percent;

    public PercentAmountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        validate(percent);
        this.percent = percent;
    }

    private void validate(long percent) {
        if (percent > 100) {
            throw new IllegalArgumentException("100% 이상으로 할인 할 수 없습니다.");
        }
        if (percent < 0) {
            throw new IllegalArgumentException("할인 퍼센트는 마이너스가 될 수 없다.");
        }
    }

    public long getPercent() {
        return percent;
    }

    public void setPercent(long percent) {
        validate(percent);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        double discountBase = 100;
        double discountRange = percent / discountBase;
        long discountedPrice = beforeDiscount - (long) (beforeDiscount * discountRange);
        return discountedPrice < 0 ? 0 : discountedPrice;
    }

    @Override
    public void update(VoucherUpdateRequestDto requestDto) {
        validate(requestDto.getDiscountPercent());
        this.percent = requestDto.getDiscountPercent();
    }

    @Override
    public String toString() {
        return "PercentAmountVoucher{" +
                "voucherId=" + super.getVoucherId() +
                ", percent=" + percent +
                '}';
    }
}
