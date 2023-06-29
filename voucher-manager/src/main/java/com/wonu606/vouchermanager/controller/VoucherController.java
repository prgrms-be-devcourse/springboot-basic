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

    public void run() {
        boolean continueProcessing = true;
        while (continueProcessing) {
            try {
                String menuName = consoleIO.selectMenu();
                continueProcessing = performMenu(menuName);
            } catch (IllegalArgumentException exception) {
                consoleIO.displayMessage(exception.getMessage());
            }
        }
        terminal();
    }

    private boolean performMenu(String menuName) {
        VoucherMenu menu = VoucherMenu.getVoucherTypeByName(menuName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        switch (menu) {
            case EXIT:
                return false;

            case LIST:
                List<Voucher> voucherList = service.getVoucherList();
                consoleIO.displayVoucherList(voucherList);
                return true;

            case CREATE:
                String type = consoleIO.selectVoucherType();
                double discount = consoleIO.readDouble("discount");
                service.createVoucher(type, UUID.randomUUID(), discount);
                return true;

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
