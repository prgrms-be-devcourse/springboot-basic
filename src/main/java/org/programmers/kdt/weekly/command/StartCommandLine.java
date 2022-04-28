package org.programmers.kdt.weekly.command;

import org.programmers.kdt.weekly.command.io.Console;
import org.programmers.kdt.weekly.command.io.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StartCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(StartCommandLine.class);

    private final Console console;
    private final CustomerCommandLine customerCommand;
    private final VoucherCommandLine voucherCommandLine;

    public StartCommandLine(
        Console console,
        CustomerCommandLine customerCommandLine,
        VoucherCommandLine voucherCommandLine) {
        this.console = console;
        this.customerCommand = customerCommandLine;
        this.voucherCommandLine = voucherCommandLine;
    }

    public void run() {
        StartCommandType startCommandType = StartCommandType.DEFAULT;

        while (startCommandType.isRunnable()) {
            this.console.printStartCommandMessage();
            var userInput = this.console.getUserInput();

            try {
                startCommandType = StartCommandType.of(userInput);
            } catch (IllegalArgumentException e) {
                logger.debug("잘못된 사용자 입력 -> {}", userInput);
                this.console.printErrorMessage(ErrorType.COMMAND);
            }

            switch (startCommandType) {
                case VOUCHER -> this.voucherCommandLine.run();
                case CUSTOMER -> this.customerCommand.run();
                case EXIT -> this.console.programExitMessage();
                default -> this.console.printErrorMessage(ErrorType.COMMAND);
            }
        }
    }
}