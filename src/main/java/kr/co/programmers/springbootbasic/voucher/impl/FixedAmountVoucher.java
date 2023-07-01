package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        super(VoucherType.FIXED_AMOUNT, voucherId, fixedAmount);
    }
    public FixedAmountVoucher(UUID voucherId, long fixedAmount, LocalDateTime createdAt) {
        super(VoucherType.FIXED_AMOUNT, voucherId, fixedAmount, createdAt);
    }

    @Override
    public long discount(long productPrice) {
        long discountPrice = productPrice - super.getAmount();
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < ZERO) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidAmountException("할인 적용 금액이 %d원으로 잘못 됐습니다.\n\n".formatted(discountPrice));
        }
    }
}
