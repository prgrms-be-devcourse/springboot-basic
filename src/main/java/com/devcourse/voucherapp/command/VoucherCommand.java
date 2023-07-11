package com.devcourse.voucherapp.command;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.voucher.VoucherMenu;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.request.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.request.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.entity.voucher.response.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.response.VouchersResponseDto;
import com.devcourse.voucherapp.view.CommonView;
import com.devcourse.voucherapp.view.VoucherView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherCommand {

    private final CommonView commonView;
    private final VoucherView voucherView;
    private final VoucherController voucherController;

    public void run() {
        voucherView.showTitle();

        String menuOption = commonView.readMenuOption(VoucherMenu.values());
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
        String typeNumber = voucherView.readVoucherTypeNumber();
        VoucherType voucherType = VoucherType.from(typeNumber);

        String message = voucherType.getMessage();
        String discountAmount = voucherView.readDiscountAmount(message);

        VoucherCreateRequestDto request = new VoucherCreateRequestDto(voucherType, discountAmount);
        VoucherResponseDto response = voucherController.create(request);

        voucherView.showVoucherCreationSuccessMessage(response);
    }

    private void readAllVouchers() {
        VouchersResponseDto response = voucherController.findAllVouchers();
        voucherView.showAllVouchers(response);
    }

    private void updateVoucher() {
        readAllVouchers();

        String id = voucherView.readVoucherIdToUpdate();
        VoucherResponseDto findResponse = voucherController.findVoucherById(id);

        String discountAmount = voucherView.readVoucherDiscountAmountToUpdate(findResponse);
        VoucherUpdateRequestDto request = new VoucherUpdateRequestDto(findResponse.getId(), findResponse.getType(), discountAmount);
        VoucherResponseDto response = voucherController.update(request);

        voucherView.showVoucherUpdateSuccessMessage(response);
    }

    private void deleteVoucher() {
        readAllVouchers();

        String id = voucherView.readVoucherIdToDelete();
        voucherController.deleteById(id);

        voucherView.showVoucherDeleteSuccessMessage();
    }
}
