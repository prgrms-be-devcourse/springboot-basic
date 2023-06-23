package org.promgrammers.springbootbasic.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {

    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String type;
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType of(String voucherType) {
        try {
            return Arrays.stream(values())
                    .filter(voucher -> voucher.type.equals(voucherType.toUpperCase()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 Voucher 타입입니다."));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Voucher Type => {}", voucherType);
            throw e;
        }
    }
}
