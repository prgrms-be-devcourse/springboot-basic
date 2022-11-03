package org.prgrms.kdt;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandStatus {
    EXIT("exit", "Type exit to exit the program."),
    CREATE("create", "Type create to create a new voucher."),
    LIST("list", "Type list to list all vouchers.");
    

    public final String command;
    private final String expression;

    CommandStatus(String command, String expression) {
        this.command = command;
        this.expression = expression;
    }

    public static CommandStatus of(String cmd) {
        return Stream.of(values())
                .filter(cmdStatus -> cmdStatus.command.equals(cmd))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 커맨드입니다."));
    }

    public static String getAllCommandExpression() {
        return Stream.of(values())
                .map(cmdStat -> cmdStat.expression)
                .collect(Collectors.joining("\n"));
    }
}
