package com.programmers.voucher.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherEnum {
    FIXED(1), PERCENT(2);

    private final int numberOfVoucherEnum;

    VoucherEnum(int numberOfVoucherEnum) {
        this.numberOfVoucherEnum = numberOfVoucherEnum;
    }

    public static VoucherEnum decideVoucherType(int number) {
        Logger logger = LoggerFactory.getLogger(VoucherEnum.class.getSimpleName());
        if (FIXED.numberOfVoucherEnum == number) {
            return FIXED;
        }
        if (PERCENT.numberOfVoucherEnum == number) {
            return PERCENT;
        }
        logger.info("지원하지 않는 버전, [user input] : {}", number);
        throw new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.");
    }
}
