package org.programmers.kdt.weekly.command;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    DEFAULT("default", "default", "init"),
    VOUCHER_CREATE("create", "start", "Type create to create a new voucher."),
    VOUCHER_LIST("list", "start", "Type list to list all vouchers."),
    CUSTOMER("customer", "start", "Type customer to customer menu"),
    WALLET("wallet", "start", "Type wallet to Voucher Wallet service"),
    EXIT("exit", "exit", "Type exit to exit the program."),
    WALLET_INSERT("insert", "wallet","Type insert to put the voucher in your wallet."),
    WALLET_LIST("list","wallet", "Type list to list all your vouchers."),
    WALLET_DELETE("delete","wallet","Type delete to delete your voucher. "),
    CUSTOMER_CREATE("create","customer","Type create to create a new customer."),
    CUSTOMER_LIST("list","customer","Type list to list all customers."),
    CUSTOMER_BLACK_LIST("blacklist","customer", "Type blacklist to list all customers."),
    CUSTOMER_TYPE_CHANGE("change", "customer","Type type to change customer Type.");

    private final String command;
    private final String programType;
    private final String description;

    CommandType(String command, String programType, String description) {
        this.command = command;
        this.programType = programType;
        this.description = description;
    }

    public String getCommandMessage() {
        return description;
    }

    public static CommandType findByCommand(String userInput) {
        return Arrays.stream(CommandType.values())
            .filter(c -> c.command.equals(userInput))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException());
    }

    public static List<CommandType> findByProgramType(String userInput) {
        return Arrays.stream(CommandType.values())
            .filter((c) -> c.programType.equals(userInput) || c.programType.equals("exit"))
            .toList();
    }
}
