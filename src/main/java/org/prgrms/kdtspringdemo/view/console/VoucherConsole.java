package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.view.console.input.Input;
import org.prgrms.kdtspringdemo.view.console.output.Output;
import org.springframework.stereotype.Component;

@Component
public class VoucherConsole {
    private static final String INIT_MESSAGE = "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers.";
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.";
    private static final String INVALID_COMMAND_MESSAGE = "잘못된 명령입니다.\n" +
            "다시 입력 부탁드립니다.";
    private final Input input;
    private final Output output;

    public VoucherConsole(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printInitMessage() {
        output.write(INIT_MESSAGE);
    }

    public String InputCommand() {
        return input.read();
    }

    public void printSystemShutdown() {
        output.write(SYSTEM_SHUTDOWN_MESSAGE);
    }

    public void printInvalidCommandSelected() {
        output.write(INVALID_COMMAND_MESSAGE);
    }
}
