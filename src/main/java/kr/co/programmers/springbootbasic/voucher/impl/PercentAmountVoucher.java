package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.NoValidDiscountPrice;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherType;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(UUID voucherId, long percentAmount) {
        super(VoucherType.PERCENT_AMOUNT, voucherId, percentAmount);
    }

    @Override
    public long discount(long totalPrice) {
        long discountPrice = totalPrice - (long) Math.floor(totalPrice * (super.getAmount() / (double) ONE_HUNDRED));
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < 0) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidDiscountPrice("할인 적용 금액이 %d원으로 잘못 됐습니다.".formatted(discountPrice));
        }
    }
}
