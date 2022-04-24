package com.programmers.springbootbasic.domain;

import com.programmers.springbootbasic.dto.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.UUID;

public enum VoucherType {

    FIXED_AMOUNT(1),
    PERCENT_DISCOUNT(2);

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final Scanner sc = new Scanner(System.in);

    private final int type;

    VoucherType(int type) {
        this.type = type;
    }

    public static VoucherDTO createVoucherDTO(VoucherType type) {
        VoucherDTO voucherDTO = null;

        switch (type) {
            case FIXED_AMOUNT -> voucherDTO = makeFixedAmountVoucher();
            case PERCENT_DISCOUNT -> voucherDTO = makePerCentDiscountVoucher();
            default -> throw new IllegalArgumentException("해당 종류의 할인권은 존재하지 않습니다.");
        }

        return voucherDTO;
    }

    private static VoucherDTO makeFixedAmountVoucher() {
        System.out.print("고정 할인 금액을 입력하세요. => ");

        try {
            Long fixedAmount = Long.valueOf(sc.next());
            return new VoucherDTO(UUID.randomUUID(), fixedAmount, null, FIXED_AMOUNT.getType());
        } catch (NumberFormatException e) {
            logger.info("잘못된 입력이 들어왔습니다.", e);
            throw new NumberFormatException(e.getMessage());
        }
    }

    private static VoucherDTO makePerCentDiscountVoucher() {
        System.out.print("할인율을 입력하세요. => ");

        try {
            Double discountPercent = Double.valueOf(sc.next());
            return new VoucherDTO(UUID.randomUUID(), null, discountPercent, PERCENT_DISCOUNT.getType());
        } catch (NumberFormatException e) {
            logger.info("잘못된 입력이 들어왔습니다.", e);
            throw new NumberFormatException(e.getMessage());
        }
    }

    public int getType() {
        return type;
    }

}