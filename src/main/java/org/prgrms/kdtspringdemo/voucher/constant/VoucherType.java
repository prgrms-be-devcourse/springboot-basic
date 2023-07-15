package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER_TYPE;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    public static VoucherType findVoucherType(String userVoucherType) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.name().equals(userVoucherType.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userVoucherType, NOT_FOUND_VOUCHER_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_VOUCHER_TYPE.getMessage());
                });
    }
}
