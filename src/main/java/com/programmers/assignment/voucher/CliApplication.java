package com.programmers.assignment.voucher;

import com.programmers.assignment.voucher.engine.controller.MenuController;
import com.programmers.assignment.voucher.engine.controller.VoucherController;
import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.util.domain.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CliApplication {
    private final MenuController menuController;

    private final VoucherController voucherController;

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);

    @Autowired
    public CliApplication(MenuController menuController, VoucherController voucherController) {
        this.menuController = menuController;
        this.voucherController = voucherController;
    }

    public void runMenu() {
        String command = "";
        while (!command.equals(Menu.EXIT)) {
            command = menuController.startMenu();
            Menu menuCommand = Menu.getMenu(command);
            logger.info("Command input : " + command);

            switch (menuCommand) {
                case CREATE_VOUCHER -> {
                    String inputDiscountWay = menuController.createVoucherCommand();
                    logger.info("Voucher select : " + inputDiscountWay);
                    runCreate(inputDiscountWay);
                }
                case LIST -> {
                    menuController.listCommand();
                }
                case EXIT -> {
                    menuController.exitCommand();
                }
                case CREATE_CUSTOMER -> {
                    menuController.createCustomerCommand();
                }
            }
        }
    }

    public void runCreate(String inputDiscountWay) {
        voucherController.makeVoucher(inputDiscountWay);
    }
}
