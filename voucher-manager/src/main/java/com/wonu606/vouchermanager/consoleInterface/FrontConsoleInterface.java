package com.wonu606.vouchermanager.consoleInterface;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.io.FrontConsoleIO;
import com.wonu606.vouchermanager.menu.ConsoleMenu;
import com.wonu606.vouchermanager.menu.CustomerMenu;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FrontConsoleInterface {

    private final CustomerConsoleInterface customerConsoleInterface;
    private final VoucherConsoleInterface voucherConsoleInterface;
    private final FrontConsoleIO frontConsoleIO;

    public FrontConsoleInterface(CustomerConsoleInterface customerConsoleInterface,
            VoucherConsoleInterface voucherConsoleInterface, FrontConsoleIO frontConsoleIO) {
        this.customerConsoleInterface = customerConsoleInterface;
        this.voucherConsoleInterface = voucherConsoleInterface;
        this.frontConsoleIO = frontConsoleIO;
    }

    public void run() {
        ConsoleMenu menu = ConsoleMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = frontConsoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                frontConsoleIO.displayMessage(exception.getMessage());
            }
        }
        terminal();
    }

    private void executeMenuAction(ConsoleMenu menu) {
        switch (menu) {
            case EXIT:
                return;
            case CUSTOMER:
                customerConsoleInterface.run();
                return;
            case VOUCHER:
                voucherConsoleInterface.run();
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private void terminal() {
        frontConsoleIO.displayMessage("곧 프로그램을 종료합니다.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        frontConsoleIO.terminal();
    }
}
