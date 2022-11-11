package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongVoucherTypeException;

import java.util.Arrays;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

public enum VoucherType {
    FIXED_VOUCHER("f"),
    PERCENT_VOUCHER("p");

    private final String voucherType;

    VoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    private String getVoucherType() {
        return voucherType;
    }

    public static VoucherType findVoucherType(String selection){
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getVoucherType().equals(selection))
                .findFirst()
                .orElseThrow(() -> {
                    throw new WrongVoucherTypeException(getSelectWrongMessage());
                }
        );
    }
}
