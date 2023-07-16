package com.prgms.VoucherApp.view;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum WalletCommand {
    CREATE(1),
    FIND_ONE(2),
    FIND_BY_CUSTOMER_ID(3),
    FIND_BY_VOUCHER_ID(4),
    DELETE(5),
    DELETE_BY_VOUCHER_ID(6),
    EXIT(7);

    private static final Map<Integer, WalletCommand> WALLET_COMMAND_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(WalletCommand::getWalletCommandNumber, Function.identity()));
    private final int commandNumber;

    WalletCommand(int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public static WalletCommand findByCommandNumber(int commandNumber) {
        return WALLET_COMMAND_MAP.get(commandNumber);
    }

    public static boolean containsWalletCommand(int commandNumber) {
        return WALLET_COMMAND_MAP.containsKey(commandNumber);
    }

    public String getWalletCommandName() {
        return name().toLowerCase();
    }

    public int getWalletCommandNumber() {
        return commandNumber;
    }
}
