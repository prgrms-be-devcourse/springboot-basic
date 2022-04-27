package org.programmers.kdt.weekly.command;

import java.util.Arrays;

public enum VoucherWalletCommandType {
    DEFAULT("default", "init"),
    WALLET_INSERT("insert", "Type insert to put the voucher in your wallet."),
    WALLET_LIST("list", "Type list to list all your vouchers."),
    WALLET_DELETE("delete", "Type delete to delete your voucher. "),
    EXIT("exit", "Type exit to exit the program.");

    private final String command;
    private final String description;

    VoucherWalletCommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommandMessage() {
        return description;
    }

    public static VoucherWalletCommandType findByCommand(String userInput) {
        return Arrays.stream(VoucherWalletCommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRunnable() {
        return this != VoucherWalletCommandType.EXIT;
    }
}
