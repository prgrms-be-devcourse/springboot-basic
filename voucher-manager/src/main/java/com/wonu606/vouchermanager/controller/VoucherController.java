package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.io.ConsoleIO;
import com.wonu606.vouchermanager.service.VoucherService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherController {

    private final VoucherService service;
    private final ConsoleIO consoleIO;

    private static boolean isExitMenu(VoucherMenu menu) {
        return menu != VoucherMenu.EXIT;
    }

    public void run() {
        VoucherMenu menu = VoucherMenu.START;
        while (isExitMenu(menu)) {
            try {
                menu = consoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (IllegalArgumentException exception) {
                consoleIO.displayMessage(exception.getMessage());
            }
        }
        terminal();
    }

    private void executeMenuAction(VoucherMenu menu) {
        switch (menu) {
            case EXIT:
                return;

            case LIST:
                List<Voucher> voucherList = service.getVoucherList();
                consoleIO.displayVoucherList(voucherList);
                return;

            case CREATE:
                String type = consoleIO.selectVoucherType();
                double discount = consoleIO.readDouble("discount");
                service.createVoucher(type, UUID.randomUUID(), discount);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private void terminal() {
        consoleIO.displayMessage("곧 프로그램을 종료합니다.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        consoleIO.terminal();
    }
}
