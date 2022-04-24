package com.programmers.springbootbasic.domain;

import com.programmers.springbootbasic.dto.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static com.programmers.springbootbasic.validation.BusinessValidator.*;

public enum VoucherType {

    FIXED_AMOUNT(1, "FIXED AMOUNT"),
    PERCENT_DISCOUNT(2, "PERCENT DISCOUNT");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final Scanner sc = new Scanner(System.in);

    private final int type;
    private final String name;

    VoucherType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static VoucherDTO createVoucherDTO(int type) throws IllegalArgumentException {
        VoucherType voucherType = findVoucherFromType(type)
                .orElseThrow(() -> new IllegalArgumentException("해당 할인권이 존재하지 않습니다."));

        VoucherDTO voucherDTO = null;

        switch (voucherType) {
            case FIXED_AMOUNT -> voucherDTO = makeFixedAmountVoucher();
            case PERCENT_DISCOUNT -> voucherDTO = makePerCentDiscountVoucher();
        }

        return voucherDTO;
    }

    private static VoucherDTO makeFixedAmountVoucher() {
        System.out.print("고정 할인 금액을 입력하세요. => ");

        try {
            Long fixedAmount = Long.valueOf(sc.next());
            
            if (validateFixedAmountVoucher(fixedAmount))
                return new VoucherDTO(UUID.randomUUID(), fixedAmount, null, FIXED_AMOUNT.type);
            else {
                logger.info("범위를 벗어난 고정 할인 금액 입력");
                throw new IllegalArgumentException(FIXED_AMOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            logger.info("고정 할인 금액에 대한 부적절한 값 입력");
            throw new NumberFormatException(INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE);
        }
    }

    private static VoucherDTO makePerCentDiscountVoucher() {
        System.out.print("할인율을 입력하세요. => ");

        try {
            int discountPercent = Integer.parseInt(sc.next());

            if (validatePercentDiscountVoucher(discountPercent))
                return new VoucherDTO(UUID.randomUUID(), null, discountPercent, PERCENT_DISCOUNT.type);
            else {
                logger.info("범위를 벗어난 고정 할인율 입력");
                throw new IllegalArgumentException(PERCENT_DISCOUNT_OUT_OF_BOUNDS_EXCEPTION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            logger.info("고정 할인율에 대한 부적절한 값 입력");
            throw new NumberFormatException(INVALID_INPUT_NUMBER_EXCEPTION_MESSAGE);
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