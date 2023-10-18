package org.programmers.springorder;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.controller.VoucherController;
import org.programmers.springorder.utils.MenuType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;

    public VoucherApplication(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while(isRunning) {
            MenuType menu = console.inputMenu();

            switch (menu) {
                case EXIT -> {
                    isRunning = false;
                    console.printMessage("프로그램이 종료되었습니다.");
                }
                case CREATE -> voucherController.createVoucher();
                case LIST -> voucherController.getVoucherList();
            }
        }
    }

}
