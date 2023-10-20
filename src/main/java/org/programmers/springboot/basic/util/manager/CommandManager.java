package org.programmers.springboot.basic.util.manager;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.controller.CustomerController;
import org.programmers.springboot.basic.domain.voucher.controller.VoucherController;
import org.programmers.springboot.basic.util.CommandType;
import org.programmers.springboot.basic.util.exception.CommandNotFoundException;
import org.programmers.springboot.basic.util.exception.ConsoleIOFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandManager implements CommandLineRunner {

    private final ConsoleIOManager consoleIOManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    @Autowired
    public CommandManager(ConsoleIOManager consoleIOManager, VoucherController voucherController, CustomerController customerController) {
        this.consoleIOManager = consoleIOManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {

        bindArgument(args);

        boolean flag = true;

        try {
            while (flag) {

                consoleIOManager.printSystem();

                String input = consoleIOManager.getInput();
                CommandType command = CommandType.valueOfCommand(input);

                switch (command) {

                    case CREATE -> createHandler();

                    case LIST -> listHandler();

                    case BLACKLIST -> blackListHandler();

                    case EXIT -> {
                            consoleIOManager.printExit();
                            flag = false;
                    }

                    default -> consoleIOManager.printErrCommand();
                }
            }
        } catch (ConsoleIOFailureException | CommandNotFoundException e) {
            log.error(e.toString());
        }
    }

    private void bindArgument(String... args) {
        if (args.length >= 2) {
            System.setProperty("spring.config.name", "application");
            System.setProperty("external.proj-dir", args[0]);
            System.setProperty("external.resource-dir", args[1]);
        }
    }

    private void createHandler() {
        this.voucherController.create();
    }

    private void listHandler() {
        this.voucherController.list();
    }

    private void blackListHandler() {
        this.customerController.blacklist();
    }
}
