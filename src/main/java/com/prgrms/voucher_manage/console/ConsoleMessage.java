package com.prgrms.voucher_manage.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum ConsoleMessage {
    VOUCHER_FIND_ID("Input voucher id you want to find."),
    VOUCHER_UPDATE_ID("Input voucher id you want to update,"),
    VOUCHER_UPDATE_PRICE("Input voucher price you want to update,"),
    VOUCHER_DELETE_ID("Input voucher id you want to delete."),

    VOUCHER_SAVE_NAME("Input new customer name."),

    CUSTOMER_SAVE_NAME("Input customer name to save"),
    CUSTOMER_UPDATE_NAME("Input customer name to update"),
    CUSTOMER_UPDATE_ID("Input customer id to update"),
    CUSTOMER_FIND_NAME("Input customer name to find.");


    private final String message;

}
