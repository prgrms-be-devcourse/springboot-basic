package org.promgrammers.springbootbasic.controller;

import org.promgrammers.springbootbasic.domain.customer.controller.CustomerController;
import org.promgrammers.springbootbasic.domain.voucher.controller.VoucherController;
import org.promgrammers.springbootbasic.domain.voucher.model.Command;
import org.promgrammers.springbootbasic.domain.wallet.controller.WalletController;
import org.promgrammers.springbootbasic.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class CommandLineController implements CommandLineRunner {

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;
    private final Console console;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    public CommandLineController(VoucherController voucherController, CustomerController customerController, WalletController walletController, Console console) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
        this.console = console;
    }

    public void run() {
        while (CommandProgramStatus.isRunning()) {
            try {
                String inputCommand = console.displayCommandGuide();
                Command command = Command.from(inputCommand);
                execute(command);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                console.print(e.getMessage());
            }
        }
    }

    private void execute(Command command) {
        switch (command) {
            case EXIT -> CommandProgramStatus.stop();
            case VOUCHER -> voucherController.execute();
            case CUSTOMER -> customerController.execute();
            case WALLET -> walletController.execute();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        run();
    }
}
