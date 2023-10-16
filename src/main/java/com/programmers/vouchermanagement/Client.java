package com.programmers.vouchermanagement;

import org.springframework.stereotype.Component;

@Component
public class Client {
    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;

    public Client(ConsoleManager consoleManager, VoucherController voucherController) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
    }

    public boolean selectMenu() {
        String menu = consoleManager.selectMenu();

        if (!validateMenu(menu)) {
            consoleManager.printExit();
            return false;
        }

        executeMenu(menu);
        return true;
    }

    private boolean validateMenu(String menu) {
        if (menu.equals("exit")) {
            return false;
        }

        return true;
    }

    public void executeMenu(String menu) {
        switch (menu) {
            case "create" -> {
                Voucher voucher = consoleManager.instructCreate();
                voucherController.create(voucher);
            }
        }
    }
}
