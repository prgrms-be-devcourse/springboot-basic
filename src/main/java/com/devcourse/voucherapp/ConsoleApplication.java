package com.devcourse.voucherapp;

import static com.devcourse.voucherapp.entity.Menu.getMenu;
import static com.devcourse.voucherapp.entity.VoucherType.getVoucherType;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.view.ConsoleView;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleApplication implements CommandLineRunner {

    private final ConsoleView consoleView;
    private final VoucherController voucherController;
    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            String menuNumber = consoleView.readMenuNumber();
            Menu selectedMenu = getMenu(menuNumber);
            executeMenu(selectedMenu);
        }
    }

    private void executeMenu(Menu selectedMenu) {
        switch (selectedMenu) {
            case CREATE -> createVoucher();
            case LIST -> listAllVouchers();
            case QUIT -> quitApplication();
        }
    }

    private void createVoucher() {
        String voucherTypeNumber = consoleView.readVoucherTypeNumber();
        VoucherType voucherType = getVoucherType(voucherTypeNumber);

        String message = voucherType.getMessage();
        String discountAmount = consoleView.readDiscountAmount(message);

        Voucher voucher = voucherController.createVoucher(voucherType, discountAmount);
        consoleView.showVoucherCreationSuccessMessage(voucher);
    }

    private void listAllVouchers() {
        Collection<Voucher> vouchers = voucherController.findAllVouchers();
        consoleView.showAllVouchers(vouchers);
    }

    private void quitApplication() {
        isRunning = false;
        consoleView.showQuitMessage();
    }
}
