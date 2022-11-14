package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;

import java.util.Arrays;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

public enum VoucherType {
    FIXED_VOUCHER("fix", "FixedAmountVoucher"),
    PERCENT_VOUCHER("percent", "PercentDiscountVoucher");

    private final String voucherType;
    private final String voucherClassName;

    VoucherType(String voucherType, String voucherClassName) {
        this.voucherType = voucherType;
        this.voucherClassName = voucherClassName;
    }

    private String getVoucherType() {
        return voucherType;
    }

    private String getVoucherClassName() {
        return voucherClassName;
    }

    public static VoucherType findVoucherTypeByInput(String selection) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getVoucherType().equals(selection))
                .findFirst()
                .orElseThrow(() -> {
                            throw new InvalidITypeInputException(getSelectWrongMessage());
                        }
                );
    }

    public static VoucherType findVoucherTypeByClassName(String className) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType ->
                        voucherType.getVoucherClassName()
                                .equals(className))
                .findFirst()
                .orElseThrow(() -> {
                            throw new InvalidITypeInputException(getSelectWrongMessage());
                        }
                );
    }
}
