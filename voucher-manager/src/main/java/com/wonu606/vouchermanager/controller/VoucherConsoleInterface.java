package com.wonu606.vouchermanager.controller;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherDto;
import com.wonu606.vouchermanager.io.ConsoleIO;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherConsoleInterface {

    private final ConsoleIO consoleIO;
    private final VoucherController controller;

    public void run() {
        VoucherMenu menu = VoucherMenu.START;
        while (isNotExitMenu(menu)) {
            try {
                menu = consoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (IllegalArgumentException exception) {
                consoleIO.displayMessage(exception.getMessage());
            }
        }
        terminal();
    }

    private boolean isNotExitMenu(VoucherMenu menu) {
        return menu != VoucherMenu.EXIT;
    }

    private void executeMenuAction(VoucherMenu menu) {
        switch (menu) {
            case EXIT:
                return;

            case LIST:
                List<Voucher> voucherList = controller.getVoucherList();
                consoleIO.displayVoucherList(voucherList);
                return;

            case CREATE:
                VoucherDto voucherDto = createVoucherDto();
                controller.createVoucher(voucherDto);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private VoucherDto createVoucherDto() {
        String type = consoleIO.selectVoucherType();
        double discountValue = consoleIO.readDouble("discount");
        return new VoucherDto(type, UUID.randomUUID(), discountValue);
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
