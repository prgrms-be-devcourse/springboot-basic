package com.prgrms.springbasic;

import com.prgrms.springbasic.constant.MenuType;
import com.prgrms.springbasic.controller.VoucherController;
import com.prgrms.springbasic.dto.CreateVoucherRequest;
import com.prgrms.springbasic.io.Console;
import com.prgrms.springbasic.io.ConsoleMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherRunner implements CommandLineRunner {
    private final Console console;
    private final VoucherController voucherController;

    public VoucherRunner(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
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
            }
        }
    }

    private CreateVoucherRequest makeCreateVoucherRequest() {
        String discountType = console.inputDiscountType();
        long discountValue = console.inputLong(ConsoleMessage.GET_DISCOUNT_VALUE);
        return new CreateVoucherRequest(discountType, discountValue);
    }
}
