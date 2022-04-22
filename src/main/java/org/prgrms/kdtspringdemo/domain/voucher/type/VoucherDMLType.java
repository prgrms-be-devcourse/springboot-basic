package org.prgrms.kdtspringdemo.domain.voucher.type;

import java.util.Arrays;

public enum VoucherDMLType {
    UPDATE("Update voucher"),
    COUNT("Count all vouchers"),
    FINDALL("Show all vouchers"),
    FINDBYID("Find voucher by id"),
    FINDBYTYPE("Find voucher by type"),
    DELETEALL("delete all vouchers"),
    NONE("Type letter correctly");

    private final String stateInfo;

    VoucherDMLType(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static VoucherDMLType of(String inputDML) {
        return Arrays.stream(VoucherDMLType.values())
                .filter(type -> String.valueOf(type).equalsIgnoreCase(inputDML))
                .findFirst()
                .orElse(NONE);
    }

    public void writeStateInfo() {
        System.out.println(stateInfo);
    }

}
