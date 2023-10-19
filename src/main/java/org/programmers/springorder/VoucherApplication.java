package org.programmers.springorder;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.customer.controller.CustomerController;
import org.programmers.springorder.voucher.controller.VoucherController;
import org.programmers.springorder.utils.MenuType;
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

        while(isRunning) {
            MenuType menu = console.inputMenu();

            switch (menu) {
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Message.EXIT_PROGRAM_MESSAGE);
                }
                case CREATE -> voucherController.createVoucher();
                case LIST -> voucherController.getVoucherList();
                case BLACK -> customerController.printBlackList();
            }
        }
    }

}
