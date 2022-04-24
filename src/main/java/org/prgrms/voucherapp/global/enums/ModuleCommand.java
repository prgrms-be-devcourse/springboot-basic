package org.prgrms.voucherapp.global.enums;

import java.util.Optional;

public enum ModuleCommand {
    EXIT("exit"),
    VOUCHER("voucher"),
    CUSTOMER("customer"),
    WALLET("wallet");

    private final String command;

    ModuleCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Optional<ModuleCommand> getMenu(String command) {
        try{
            return Optional.of(ModuleCommand.valueOf(command.toUpperCase()));
        } catch(IllegalArgumentException e){
            return Optional.empty();
        }
    }
}
