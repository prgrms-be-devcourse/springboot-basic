package org.programmers.springorder;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.controller.CustomerController;
import org.programmers.springorder.utils.ExceptionHandler;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.controller.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherApplication(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            MenuType menu = ExceptionHandler.input(Console::inputMenu);

            switch (menu) {
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Message.EXIT_PROGRAM_MESSAGE);
                }
//                case VOUCHER -> voucherController.run();
                case CUSTOMER -> customerController.run();
//                case WALLET -> walletController.run();
            }
        }
    }

}
