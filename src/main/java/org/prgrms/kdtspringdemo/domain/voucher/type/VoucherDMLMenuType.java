package org.prgrms.kdtspringdemo.domain.voucher.type;

import java.util.Arrays;

public enum VoucherDMLMenuType {
    UPDATE("Update voucher"),
    COUNT("Count all vouchers"),
    FINDALL("Show all vouchers"),
    FINDBYID("Find voucher by id"),
    FINDBYTYPE("Find voucher by type"),
    DELETEALL("delete all vouchers"),
    NONE("Type letter correctly");

    private final String stateInfo;

    VoucherDMLMenuType(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static VoucherDMLMenuType of(String inputDML) {
        return Arrays.stream(VoucherDMLMenuType.values())
                .filter(type -> String.valueOf(type).equalsIgnoreCase(inputDML))
                .findFirst()
                .orElse(NONE);
    }
}
