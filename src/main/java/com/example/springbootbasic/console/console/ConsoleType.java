package com.example.springbootbasic.console.console;

import com.example.springbootbasic.controller.ControllerType;

import java.util.Arrays;
import java.util.Optional;

import static com.example.springbootbasic.controller.ControllerType.CUSTOMER;
import static com.example.springbootbasic.controller.ControllerType.VOUCHER;

public enum ConsoleType {
    VOUCHER_MENU(VOUCHER, "voucher-menu"),
    VOUCHER_CREATE_MENU(VOUCHER, "create"),
    VOUCHER_CREATE(VOUCHER, "create-voucher"),
    VOUCHER_LIST(VOUCHER, "list"),
    VOUCHER_EXIT(VOUCHER, "exit"),
    CUSTOMER_MENU(CUSTOMER, "customer-menu"),
    CUSTOMER_BLACK_LIST(CUSTOMER, "customer-black-list");

    private final ControllerType controllerType;
    private final String type;

    ConsoleType(ControllerType controllerType, String type) {
        this.controllerType = controllerType;
        this.type = type;
    }

    public static Optional<ConsoleType> findConsoleTypeBy(String findType) {
        return Arrays.stream(ConsoleType.values())
                .filter(type -> type.type.equals(findType))
                .findFirst();
    }

    public ControllerType getControllerType() {
        return controllerType;
    }

    public String getType() {
        return type;
    }
}
