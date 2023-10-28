package com.programmers.vouchermanagement;

import com.programmers.vouchermanagement.customer.presentation.CustomerController;
import com.programmers.vouchermanagement.utils.Command;
import com.programmers.vouchermanagement.exception.CommandNotFoundException;
import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.presentation.VoucherController;
import com.programmers.vouchermanagement.wallet.presentation.WalletController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagementController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public VoucherManagementController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherController voucherController, CustomerController customerController, WalletController walletController) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) {

        logger.info("Start Voucher Program.");

        boolean chooseExit = false;

        while (!chooseExit) {

            consoleOutputManager.printCommandMenu();

            Command command;
            String input = consoleInputManager.inputString();

            try {
                command = Command.getCommandByNumber(input);

            } catch (CommandNotFoundException e) {
                logger.error(e.getMessage() + "Console Input : " + input, e);

                consoleOutputManager.printEnterAgain(e.getMessage());
                continue;
            }

            switch (command) {

                case CREATE_VOUCHER -> voucherController.createVoucher();

                case LIST_VOUCHER -> voucherController.readAllVoucher();

                case ONE_VOUCHER -> voucherController.readVoucherById();

                case UPDATE_VOUCHER -> voucherController.updateVoucher();

                case DELETE_VOUCHER -> voucherController.removeAllVoucher();

                case BLACKLIST -> customerController.readAllBlackList();

                case CREATE_WALLET -> walletController.createWallet();

                case LIST_WALLET_VOUCHER -> walletController.readVouchersByCustomer();

                case LIST_WALLET_CUSTOMER -> walletController.readCustomersByVoucher();

                case DELETE_WALLET -> walletController.removeWalletsByCustomer();

                case EXIT -> {
                    consoleOutputManager.printExit();
                    chooseExit = true;
                }
            }
        }

        logger.info("Exit Voucher Program.");
    }
}
