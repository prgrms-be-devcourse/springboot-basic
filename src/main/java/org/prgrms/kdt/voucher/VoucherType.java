package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum VoucherType {
    PERCENTAGE("Percent"),
    FIXED("Fixed");

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    private final String strVoucherType;

    VoucherType(String strVoucherType) {
        this.strVoucherType = strVoucherType;
    }

    public static VoucherType findVoucher(String inputString) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.strVoucherType.equals(inputString))
                .findAny()
                .orElseThrow(() -> {
                    logger.error("Can't find voucher type");
                    return new IllegalArgumentException();
                });
    }

    @Override
    public String toString() {
        return strVoucherType;
    }
}
