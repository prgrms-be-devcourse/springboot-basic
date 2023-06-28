package com.devcourse.voucherapp;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.view.ViewManager;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineApplication implements CommandLineRunner {

    private final ViewManager viewManager;
    private final VoucherController voucherController;
    private boolean isRunning = true;

    @Override
    public void run(String... args) {
        while (isRunning) {
            try {
                String menuNumber = viewManager.readMenuNumber();
                Menu selectedMenu = Menu.of(menuNumber);
                executeMenu(selectedMenu);
            } catch (Exception e) {
                String message = e.getMessage();
                log.error(message);
                viewManager.showExceptionMessage(message);
            }
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
        String voucherTypeNumber = viewManager.readVoucherTypeNumber();
        VoucherType voucherType = VoucherType.of(voucherTypeNumber);

        String message = voucherType.getMessage();
        String discountAmount = viewManager.readDiscountAmount(message);

        Voucher voucher = voucherController.createVoucher(voucherType, discountAmount);
        viewManager.showVoucherCreationSuccessMessage(voucher);
    }

    private void listAllVouchers() {
        Collection<Voucher> vouchers = voucherController.findAllVouchers();
        viewManager.showAllVouchers(vouchers);
    }

    private void quitApplication() {
        isRunning = false;
        viewManager.showQuitMessage();
    }
}
