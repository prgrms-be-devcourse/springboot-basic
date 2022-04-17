package org.prgms.voucheradmin.domain.console.enums;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandAboutVoucherWallet {
    ALLOCATE_VOUCHER("1", "allocate voucher"),
    FIND_ALLOCATED_VOUCHER("2", "find allocated vouchers"),
    FIND_VOUCHER_OWNER("3", "find voucher owners"),
    DELETE_CUSTOMER_VOUCHER("4", "delete customer's voucher");

    private final String commandId;
    private final String commandName;
    private static final Map<String, CommandAboutVoucherWallet> commandAboutVoucherWallet =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(commandAboutVoucherWallet -> commandAboutVoucherWallet.commandId, Function.identity())));

    CommandAboutVoucherWallet(String commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return commandId+". "+commandName;
    }

    public static Optional<CommandAboutVoucherWallet> findCommandAboutVoucherWallet(String selectedCommandId) {
        return Optional.ofNullable(commandAboutVoucherWallet.get(selectedCommandId));
    }
}
