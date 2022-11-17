package org.prgrms.springorder.console.core;

import java.util.Arrays;
import java.util.Objects;

public enum Command {

    EXIT("exit"),
    DISPLAY("display"),
    CREATE("create"),
    LIST("list"),
    BLACKLIST("black-list"),
    CREATE_CUSTOMER("create-customer"),
    POST_CUSTOMER_VOUCHERS("post-allocate-voucher"),
    GET_CUSTOMER_VOUCHERS("get-customer-vouchers"),
    DELETE_CUSTOMER_VOUCHERS("delete-customer-vouchers"),
    GET_VOUCHER_WITH_CUSTOMER("get-voucher-with-customer")
    ;

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public static Command of(String inputCommand) {
        return Arrays.stream(values())
            .filter(e -> Objects.equals(e.command, inputCommand.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 명령어 입력입니다. input : " + inputCommand));
    }

}
