package com.programmers.springbootbasic.domain;

import com.programmers.springbootbasic.controller.dto.Benefit;
import com.programmers.springbootbasic.io.ConsoleInput;
import com.programmers.springbootbasic.io.StandardInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.validation.VoucherValidator.*;

public enum VoucherType {

    FIXED_AMOUNT(1, "FIXED AMOUNT"),
    PERCENT_DISCOUNT(2, "PERCENT DISCOUNT");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final StandardInput input = new ConsoleInput();

    private final int type;
    private final String name;

    VoucherType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Voucher createVoucher(int type) throws IllegalArgumentException {
        VoucherType voucherType = findVoucherFromType(type)
                .orElseThrow(() -> new IllegalArgumentException("해당 할인권이 존재하지 않습니다."));

        Voucher voucher = null;

        switch (voucherType) {
            case FIXED_AMOUNT -> voucher = makeFixedAmountVoucher();
            case PERCENT_DISCOUNT -> voucher = makePercentDiscountVoucher();
        }

        return voucher;
    }

    private static Voucher makeFixedAmountVoucher() {
        try {
            long fixedAmount = Long.parseLong(input.promptInput("고정 할인 금액을 입력하세요."));
            
            if (isValidFixedAmountVoucher(fixedAmount))
                return new Voucher(UUID.randomUUID(), fixedAmount, null, FIXED_AMOUNT.type);
            else {
                logger.info("범위를 벗어난 고정 할인 금액 입력");
                throw new IllegalArgumentException(FIXED_AMOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            logger.info("고정 할인 금액에 대한 부적절한 값 입력");
            throw new NumberFormatException(INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE);
        }
    }

    private static Voucher makePercentDiscountVoucher() {
        try {
            int discountPercent = Integer.parseInt(input.promptInput("할인율을 입력하세요."));

            if (isValidPercentDiscountVoucher(discountPercent))
                return new Voucher(UUID.randomUUID(), null, discountPercent, PERCENT_DISCOUNT.type);
            else {
                logger.info("범위를 벗어난 고정 할인율 입력");
                throw new IllegalArgumentException(PERCENT_DISCOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            logger.info("고정 할인율에 대한 부적절한 값 입력");
            throw new NumberFormatException(INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE);
        }
    }

    public static Voucher createVoucher(Benefit benefit) throws IllegalArgumentException {
        VoucherType voucherType = findVoucherFromType(benefit.getType())
                .orElseThrow(() -> new IllegalArgumentException("해당 할인권이 존재하지 않습니다."));

        Voucher voucher = null;

        switch (voucherType) {
            case FIXED_AMOUNT -> voucher = makeFixedAmountVoucher(benefit);
            case PERCENT_DISCOUNT -> voucher = makePercentDiscountVoucher(benefit);
        }

        return voucher;
    }

    private static Voucher makeFixedAmountVoucher(Benefit benefit) {
        long fixedAmount = benefit.getFixedAmount().orElseThrow(() -> new IllegalArgumentException("값을 입력하세요."));
        if (isValidFixedAmountVoucher(fixedAmount))
            return new Voucher(UUID.randomUUID(), fixedAmount, null, FIXED_AMOUNT.type);
        else {
            logger.info("범위를 벗어난 고정 할인 금액 입력");
            throw new IllegalArgumentException(FIXED_AMOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
        }
    }

    private static Voucher makePercentDiscountVoucher(Benefit benefit) {
        int percentDiscount = benefit.getPercentDiscount().orElseThrow(() -> new IllegalArgumentException("값을 입력하세요."));
        if (isValidPercentDiscountVoucher(percentDiscount))
            return new Voucher(UUID.randomUUID(), null, percentDiscount, PERCENT_DISCOUNT.type);
        else {
            logger.info("범위를 벗어난 고정 할인율 입력");
            throw new IllegalArgumentException(PERCENT_DISCOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
        }
    }

    public static String getFormalName(int type) {
        Optional<VoucherType> voucherFromType = findVoucherFromType(type);

        return voucherFromType.map(voucherType -> voucherType.name).orElse(null);
    }

    private static Optional<VoucherType> findVoucherFromType(int type) {
        return Arrays.stream(values())
                .filter(voucher -> voucher.type == type)
                .findFirst();
    }

}