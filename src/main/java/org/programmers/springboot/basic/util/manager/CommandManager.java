package org.programmers.springboot.basic.util.manager;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.controller.CustomerController;
import org.programmers.springboot.basic.domain.voucher.controller.VoucherController;
import org.programmers.springboot.basic.domain.wallet.controller.WalletController;
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
    private final WalletController walletController;

    @Autowired
    public CommandManager(ConsoleIOManager consoleIOManager, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.consoleIOManager = consoleIOManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) {

        boolean flag = true;

        try {
            while (flag) {

                consoleIOManager.printSystem();

                String input = consoleIOManager.getInput();
                CommandType command = CommandType.ERROR;
                try {
                    command = CommandType.valueOfCommand(input);
                    log.info("Command '{}' invoked in voucherManagement Program", command);
                } catch (CommandNotFoundException e) {
                    log.warn(e.toString());
                    flag = false;
                }

                switch (command) {

                    case VOUCHER -> voucherHandler();
                    case CUSTOMER -> customerHandler();
                    case EXIT -> {
                        consoleIOManager.printExit();
                        flag = false;
                    }
                    case ERROR -> consoleIOManager.printErrCommand();
                }
            }
        } catch (ConsoleIOFailureException e) {
            log.error(e.toString());
        }
    }

    private void voucherHandler() {

        boolean flag = true;

        try {
            while (flag) {
                consoleIOManager.printVoucherSystem();

                String input = consoleIOManager.getInput();
                CommandType command = CommandType.ERROR;
                try {
                    command = CommandType.valueOfCommand(input);
                    log.info("Command '{}' invoked in voucherHandler", command);
                } catch (CommandNotFoundException e) {
                    log.warn(e.toString());
                }

                switch (command) {

                    case CREATE -> voucherController.create();
                    case LIST -> voucherController.list();
                    case FIND -> voucherController.find();
                    case CUSTOMER -> walletController.findCustomerListByVoucherId();
                    case UPDATE -> voucherController.update();
                    case DELETE -> voucherController.delete();
                    case DELETE_ALL -> voucherController.deleteAll();
                    case BACK -> {
                        consoleIOManager.printBackHandler();
                        flag = false;
                    }
                    default -> consoleIOManager.printErrCommand();
                }
            }
        } catch (ConsoleIOFailureException e) {
            log.error(e.toString());
        }
    }

    private void customerHandler() {

        boolean flag = true;

        try {
            while (flag) {
                consoleIOManager.printCustomerSystem();

                String input = consoleIOManager.getInput();
                CommandType command = CommandType.ERROR;
                try {
                    command = CommandType.valueOfCommand(input);
                } catch (CommandNotFoundException e) {
                    log.warn(e.toString());
                }

                switch (command) {

                    case CREATE -> customerController.create();
                    case LIST -> customerController.list();
                    case BLACKLIST -> customerController.blacklist();
                    case ASSIGN -> walletController.addWallet();
                    case WALLET -> walletController.findVoucherListByEmail();
                    case REMOVE -> walletController.removeVoucherFromWallet();
                    case DELETE -> customerController.delete();
                    case ADD_BLACK -> customerController.addToBlackList();
                    case DELETE_BLACK -> customerController.deleteFromBlackList();
                    case DELETE_ALL -> customerController.deleteAll();
                    case BACK -> {
                        consoleIOManager.printBackHandler();
                        flag = false;
                    }
                    default -> consoleIOManager.printErrCommand();
                }
            }
        } catch (ConsoleIOFailureException e) {
            log.error(e.toString());
        }
    }
}
