package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.voucher.VoucherMenu;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.request.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.request.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.voucher.response.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.response.VouchersResponseDto;
import com.devcourse.voucherapp.view.ViewManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherCommand {

    private final ViewManager viewManager;
    private final VoucherController voucherController;

    public void run() {
        String menuOption = viewManager.readMenuOption(VoucherMenu.values());
        VoucherMenu selectedMenu = VoucherMenu.from(menuOption);
        executeMenu(selectedMenu);
    }

    private void executeMenu(VoucherMenu selectedMenu) {
        switch (selectedMenu) {
            case CREATE -> createVoucher();
            case READ_ALL -> readAllVouchers();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
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
}
