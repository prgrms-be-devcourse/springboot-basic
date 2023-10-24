package org.prgms.kdtspringweek1.voucher.entity;

import org.prgms.kdtspringweek1.exception.InputExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED_AMOUNT(1, "fixed amount voucher", "amount"),
    PERCENT_DISCOUNT(2, "percent discount voucher", "percent");

    private long num;
    private String name;
    private String unit;
    private final static Logger logger = LoggerFactory.getLogger(VoucherType.class);

    VoucherType(long num, String name, String unit) {
        this.num = num;
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public static VoucherType getValueByNum(long num) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.num == num) {
                return voucherType;
            }
        }
        logger.debug("Invalid Voucher Type -> {}", num);
        throw new IllegalArgumentException(InputExceptionCode.INVALID_VOUCHER_TYPE.getMessage());
    }
}