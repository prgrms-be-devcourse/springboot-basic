package com.prgrms.vouchermanagement.command;

import java.util.Arrays;

public enum WalletCommand {
    ADD_VOUCHER(1), FIND_VOUCHERS(2), REMOVE_VOUCHER(3), FIND_CUSTOMER(4);

    private final int order;

    WalletCommand(int order) {
        this.order = order;
    }

    public static WalletCommand getWalletCommand(int order) throws IllegalArgumentException {
        return Arrays.stream(WalletCommand.values()).filter(v -> v.order == order)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("order와 매칭되는 WalletCommand가 없습니다."));
    }

    public static boolean contain(int order) {
        try {
            getWalletCommand(order);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public int getOrder() {
        return order;
    }
}
