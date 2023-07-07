package kr.co.programmers.springbootbasic.voucher.domain.impl;

import kr.co.programmers.springbootbasic.voucher.exception.NoValidDiscountPrice;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentAmountVoucher extends Voucher {

    public PercentAmountVoucher(UUID voucherId, long percentAmount) {
        super(VoucherType.PERCENT_AMOUNT, voucherId, percentAmount, null);
    }

    public PercentAmountVoucher(UUID voucherId, long percentAmount, LocalDateTime createdAt) {
        super(VoucherType.PERCENT_AMOUNT, voucherId, percentAmount, createdAt, null);
    }

    public PercentAmountVoucher(UUID voucherId, long percentAmount, LocalDateTime createdAt, UUID walletId) {
        super(VoucherType.PERCENT_AMOUNT, voucherId, percentAmount, createdAt, walletId);
    }

    @Override
    public long discount(long productPrice) {
        long discountPrice = productPrice - (long) Math.ceil(productPrice * (super.getAmount() / (double) ONE_HUNDRED));
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < ZERO) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidDiscountPrice("할인 적용 금액이 %d원으로 잘못 됐습니다.\n\n".formatted(discountPrice));
        }
    }
}
