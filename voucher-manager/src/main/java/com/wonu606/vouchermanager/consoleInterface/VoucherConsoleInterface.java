package com.wonu606.vouchermanager.consoleInterface;

import com.wonu606.vouchermanager.controller.VoucherController;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.io.VoucherConsoleIO;
import com.wonu606.vouchermanager.menu.VoucherMenu;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherConsoleInterface {

    private final VoucherConsoleIO voucherConsoleIO;
    private final VoucherController controller;

    public void run() {
        VoucherMenu menu = VoucherMenu.START;
        while (isNotExitMenu(menu)) {
            try {
                menu = voucherConsoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                voucherConsoleIO.displayMessage(exception.getMessage());
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
                voucherConsoleIO.displayVoucherList(voucherList);
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
        String type = voucherConsoleIO.selectVoucherType();
        double discountValue = voucherConsoleIO.readDouble("discount");
        return new VoucherDto(type, discountValue);
    }

    private void terminal() {
        voucherConsoleIO.displayMessage("곧 프로그램을 종료합니다.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        voucherConsoleIO.terminal();
    }
}
