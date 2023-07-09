package com.devcourse.voucherapp;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.dto.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.dto.VouchersResponseDto;
import com.devcourse.voucherapp.view.ViewManager;
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
                Menu selectedMenu = Menu.from(menuNumber);
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
            case READ -> readAllVouchers();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
            case QUIT -> quitApplication();
        }
    }

    private void createVoucher() {
        String typeNumber = viewManager.readVoucherTypeNumber();
        VoucherType voucherType = VoucherType.from(typeNumber);

        String message = voucherType.getMessage();
        String discountAmount = viewManager.readDiscountAmount(message);

        VoucherCreateRequestDto request = new VoucherCreateRequestDto(voucherType, discountAmount);
        VoucherResponseDto response = voucherController.create(request);

        viewManager.showVoucherCreationSuccessMessage(response);
    }

    private void readAllVouchers() {
        VouchersResponseDto response = voucherController.findAllVouchers();
        viewManager.showAllVouchers(response);
    }

    private void updateVoucher() {
        readAllVouchers();

        String id = viewManager.readVoucherIdToUpdate();
        VoucherResponseDto findResponse = voucherController.findVoucherById(id);

        String discountAmount = viewManager.readVoucherDiscountAmountToUpdate(findResponse);
        VoucherUpdateRequestDto request = new VoucherUpdateRequestDto(findResponse.getId(), findResponse.getType(), discountAmount);
        VoucherResponseDto response = voucherController.update(request);

        viewManager.showVoucherUpdateSuccessMessage(response);
    }

    private void deleteVoucher() {
        readAllVouchers();

        String id = viewManager.readVoucherIdToDelete();
        voucherController.deleteById(id);

        viewManager.showVoucherDeleteSuccessMessage();
    }

    private void quitApplication() {
        isRunning = false;
        viewManager.showQuitMessage();
    }
}
