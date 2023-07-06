package com.devcourse.voucherapp;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.view.ViewManager;
import java.util.List;
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
        String typeNumber = viewManager.readVoucherTypeNumber();
        VoucherType voucherType = VoucherType.of(typeNumber);

        String message = voucherType.getMessage();
        String discountAmount = viewManager.readDiscountAmount(message);

        VoucherCreateRequestDto request = new VoucherCreateRequestDto(voucherType, discountAmount);
        Voucher voucher = voucherController.create(request);

        viewManager.showVoucherCreationSuccessMessage(voucher);
    }

    private void listAllVouchers() {
        List<Voucher> vouchers = voucherController.findAllVouchers();
        viewManager.showAllVouchers(vouchers);
    }

    private void quitApplication() {
        isRunning = false;
        viewManager.showQuitMessage();
    }
}
