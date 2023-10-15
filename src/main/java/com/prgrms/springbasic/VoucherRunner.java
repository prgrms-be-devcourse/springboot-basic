package com.prgrms.springbasic;

import com.prgrms.springbasic.constant.MenuType;
import com.prgrms.springbasic.domain.customer.controller.CustomerController;
import com.prgrms.springbasic.domain.voucher.controller.VoucherController;
import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.io.Console;
import com.prgrms.springbasic.io.ConsoleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class VoucherRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(VoucherRunner.class);

    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherRunner(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(String... args) {

        logger.warn("start program");

        boolean isRunning = true;
        MenuType menuType;

        while (isRunning) {
            console.printConsoleMessage(ConsoleMessage.START_VOUCHER_PROGRAM);
            menuType = MenuType.find(console.inputMenuType());
            switch (menuType) {
                case EXIT -> isRunning = false;
                case CREATE -> {
                    console.printConsoleMessage(ConsoleMessage.CREATE_VOUCHER);
                    voucherController.saveVoucher(makeCreateVoucherRequest());
                }
                case LIST -> voucherController.findAll();
                case BLACK_LIST -> customerController.findAllBlackLists();
            }
        }
    }

    private CreateVoucherRequest makeCreateVoucherRequest() {
        String discountType = console.inputDiscountType();
        long discountValue = console.inputLong(ConsoleMessage.GET_DISCOUNT_VALUE);
        return new CreateVoucherRequest(discountType, discountValue);
    }
}
