package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.exception.NoValidDiscountPrice;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import kr.co.programmers.springbootbasic.voucher.VoucherValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentAmountVoucher.class);

    private final UUID voucherId;
    private final long percentAmount;

    public PercentAmountVoucher(UUID voucherId, long percentAmount) {
        logger.info("고정 할인률 바우처를 생성합니다...");

        checkValidPercentAmount(percentAmount);
        this.voucherId = voucherId;
        this.percentAmount = percentAmount;
    }

    @Override
    public long discount(long totalPrice) {
        long discountPrice = totalPrice - (long) Math.floor(totalPrice * (percentAmount / (double) VoucherValue.ONE_HUNDRED));
        checkValidDiscount(discountPrice);

        return discountPrice;
    }

    private void checkValidDiscount(long discountPrice) {
        if (discountPrice < 0) {
            logger.warn("할인이 적용된 금액이 {}원으로 잘못 됐습니다.", discountPrice);
            throw new NoValidDiscountPrice(MessageFormat.format(VoucherValue.NO_VALID_DISCOUNT_PRICE, discountPrice));
        }
    }

    @Override
    public VoucherResponseDto getVoucherInfo() {
        return new VoucherResponseDto(voucherId, VoucherType.PERCENT_AMOUNT, percentAmount);
    }

    private void checkValidPercentAmount(long percentAmount) {
        if (percentAmount <= VoucherValue.ZERO || percentAmount >= VoucherValue.ONE_HUNDRED) {
            logger.warn("사용자가 잘못된 값인 {}%를 입력했습니다.", percentAmount);
            throw new NoValidAmountException(VoucherValue.NO_VALID_PERCENT_AMOUNT);
        }
    }
}
