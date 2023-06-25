package kr.co.programmers.springbootbasic.voucher.impl;


import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import kr.co.programmers.springbootbasic.voucher.VoucherValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        logger.info("고정 금액 바우처 생성을 시작합니다...");

        checkValidFixedAmount(fixedAmount);
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public long discount(long totalPrice) {
        long discountPrice = totalPrice - fixedAmount;
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < 0) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidAmountException(MessageFormat.format(VoucherValue.NO_VALID_DISCOUNT_PRICE, discountPrice));
        }
    }

    @Override
    public VoucherResponseDto getVoucherInfo() {
        return new VoucherResponseDto(voucherId, VoucherType.FIXED_AMOUNT, fixedAmount);
    }

    private void checkValidFixedAmount(long amount) {
        if (amount <= VoucherValue.ZERO) {
            logger.warn("사용자가 잘못된 값인 {}원을 입력했습니다.", amount);
            throw new NoValidAmountException(VoucherValue.NO_VALID_FIXED_AMOUNT);
        }
    }
}
