package org.prgrms.vouchermanagement.consoleController;

import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Controller implements CommandLineRunner {

    private final ConsoleInput consoleInput = new ConsoleInput();
    private final ConsoleOutput consoleOutput = new ConsoleOutput();

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;


    public Controller(VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printWelcomeMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case VOUCHER -> voucher();
                case CUSTOMER -> customer();
                case WALLET -> wallet();
                case EXIT -> notExitCommand = exit();
            }
        }
    }

    private void voucher() {
        voucherController.voucher();
    }

    private void customer() {
        customerController.customer();
    }

    private void wallet() {
        walletController.wallet();
    }

    private boolean exit() {
        consoleOutput.printExitMessage();
        return false;
    }
}
