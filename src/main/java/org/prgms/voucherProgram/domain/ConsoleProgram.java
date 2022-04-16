package org.prgms.voucherProgram.domain;

import org.prgms.voucherProgram.domain.menu.ConsoleMenuType;
import org.prgms.voucherProgram.domain.program.CustomerProgram;
import org.prgms.voucherProgram.domain.program.VoucherProgram;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class ConsoleProgram {

    private final CustomerProgram customerProgram;
    private final VoucherProgram voucherProgram;
    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleProgram(CustomerProgram customerProgram, VoucherProgram voucherProgram, Console console) {
        this.customerProgram = customerProgram;
        this.voucherProgram = voucherProgram;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            ConsoleMenuType consoleMenuType = inputMenu();
            switch (consoleMenuType) {
                case VOUCHER -> voucherProgram.run();
                case CUSTOMER -> customerProgram.run();
                case EXIT -> isNotEndProgram = false;
            }
        }
    }

    private ConsoleMenuType inputMenu() {
        while (true) {
            try {
                return ConsoleMenuType.from(inputView.inputConsoleMenu());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
