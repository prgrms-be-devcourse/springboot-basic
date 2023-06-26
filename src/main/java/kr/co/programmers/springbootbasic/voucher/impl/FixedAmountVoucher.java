package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import kr.co.programmers.springbootbasic.voucher.VoucherValue;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        super(VoucherType.FIXED_AMOUNT, voucherId, fixedAmount);
    }

    @Override
    public long discount(long totalPrice) {
        long discountPrice = totalPrice - super.amount;
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < 0) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidAmountException(MessageFormat.format(VoucherValue.NO_VALID_DISCOUNT_PRICE, discountPrice));
        }
    }
}
