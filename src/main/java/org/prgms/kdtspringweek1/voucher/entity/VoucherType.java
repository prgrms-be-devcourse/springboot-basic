package org.prgms.kdtspringweek1.voucher.entity;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED_AMOUNT("fixed amount voucher", "amount"),
    PERCENT_DISCOUNT("percent discount voucher", "percent");

    private String name;
    private String unit;
    private final static Logger logger = LoggerFactory.getLogger(VoucherType.class);


    VoucherType(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public static VoucherType getVoucherTypeByName(String name) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.name.equalsIgnoreCase(name)) {
                return voucherType;
            }
        }
        logger.debug("Invalid Function Type -> {}", name);
        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_FUNCTION_TYPE.getMessage());
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}