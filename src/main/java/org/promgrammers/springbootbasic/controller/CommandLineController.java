package org.promgrammers.springbootbasic.controller;

import org.promgrammers.springbootbasic.domain.customer.controller.CustomerCommandController;
import org.promgrammers.springbootbasic.domain.voucher.controller.VoucherCommandController;
import org.promgrammers.springbootbasic.domain.voucher.model.Command;
import org.promgrammers.springbootbasic.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class CommandLineController implements CommandLineRunner {

    private final VoucherCommandController voucherCommandController;
    private final CustomerCommandController customerCommandController;
    private final Console console;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineController.class);

    public CommandLineController(VoucherCommandController voucherCommandController, CustomerCommandController customerCommandController, Console console) {
        this.voucherCommandController = voucherCommandController;
        this.customerCommandController = customerCommandController;
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
            case VOUCHER -> voucherCommandController.execute();
            case CUSTOMER -> customerCommandController.execute();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        run();
    }
}