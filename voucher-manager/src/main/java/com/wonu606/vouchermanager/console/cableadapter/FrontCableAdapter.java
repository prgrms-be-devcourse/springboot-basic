package com.wonu606.vouchermanager.console.cableadapter;

import com.wonu606.vouchermanager.console.controllercable.customer.CustomerControllerCable;
import com.wonu606.vouchermanager.console.controllercable.voucher.VoucherControllerCable;
import org.springframework.stereotype.Component;

@Component
public class FrontCableAdapter {

    private final CustomerControllerCable customerControllerCable;
    private final VoucherControllerCable voucherControllerCable;
    private final FrontConsoleIo consoleIo;

    public FrontCableAdapter(CustomerControllerCable customerControllerCable,
            VoucherControllerCable voucherControllerCable, FrontConsoleIo consoleIo) {
        this.customerControllerCable = customerControllerCable;
        this.voucherControllerCable = voucherControllerCable;
        this.consoleIo = consoleIo;
    }

    public void run() {
        ConsoleMenu menu = ConsoleMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = consoleIo.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                consoleIo.displayMessage(exception.getMessage());
            }
        }
        terminal();
    }

    private void executeMenuAction(ConsoleMenu menu) {
        switch (menu) {
            case EXIT:
                return;
            case CUSTOMER:
                customerControllerCable.run();
                return;
            case VOUCHER:
                voucherControllerCable.run();
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private void terminal() {
        consoleIo.displayMessage("곧 프로그램을 종료합니다.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        consoleIo.terminal();
    }
}
