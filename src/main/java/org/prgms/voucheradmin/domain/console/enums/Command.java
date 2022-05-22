package org.prgms.voucheradmin.domain.console.enums;

public enum Command {
    EXIT("exit"), VOUCHER("voucher"), CUSTOMER("customer"), VOUCHER_WALLET("voucher wallet"), BLACKLIST("blacklist");

    private final String command;

    Command(String command) {
        this.command = command;
    }
}
