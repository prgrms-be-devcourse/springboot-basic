package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongVoucherTypeException;

import java.util.Arrays;

public enum VoucherType {
    FIXED_VOUCHER("1"),
    PERCENT_VOUCHER("2"),
    GO_BACK("3");

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
                    throw new WrongVoucherTypeException(MessageType.SELECT_WRONG.getMessage());
                }
        );
    }
}
