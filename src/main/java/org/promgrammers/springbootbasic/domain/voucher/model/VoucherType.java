package org.promgrammers.springbootbasic.domain.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {

    FIXED("FIXED","1"),
    PERCENT("PERCENT","2");

    private final String type;
    private final String typeNumber;
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    VoucherType(String type,String typeNumber) {
        this.type = type;
        this.typeNumber = typeNumber;
    }

    public static VoucherType of(String voucherTypeNumber) {
        try {
            return Arrays.stream(values())
                    .filter(voucher -> voucher.typeNumber.equals(voucherTypeNumber))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 Voucher 타입입니다. => " + voucherTypeNumber));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
