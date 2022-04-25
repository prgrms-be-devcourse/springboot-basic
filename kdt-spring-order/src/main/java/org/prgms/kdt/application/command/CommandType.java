package org.prgms.kdt.application.command;

import java.util.Arrays;

public enum CommandType {
    EXIT("exit"),
    CREATE_CUSTOMER("createVoucher"),
    GET_CUSTOMER_LIST("getCustomerList"),
    CREATE_VOUCHER("createVoucher"),
    GET_VOUCHER_LIST("getVoucherList");

    private final String type;

    CommandType(String type) {
        this.type = type;
    }

    public static CommandType findCommandType (String type) {
        return Arrays.stream(values())
                .filter(i -> i.type.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입니다."));
    }
}
