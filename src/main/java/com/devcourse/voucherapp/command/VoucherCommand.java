package com.devcourse.voucherapp.command;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.controller.VoucherController;
import com.devcourse.voucherapp.entity.voucher.VoucherMenu;
import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherCreateRequestDto;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherUpdateRequestDto;
import com.devcourse.voucherapp.exception.VoucherException;
import com.devcourse.voucherapp.view.VoucherView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherCommand {

    private final VoucherView voucherView;
    private final VoucherController voucherController;
    private boolean isRunning;

    public void run() {
        isRunning = true;

        while (isRunning) {
            voucherView.showMenu();

            try {
                String menuOption = voucherView.readUserInput();
                VoucherMenu selectedMenu = VoucherMenu.from(menuOption);
                executeMenu(selectedMenu);
            } catch (VoucherException e) {
                log.error("할인권 메뉴에서 예외 발생 - {} | '{}' | 사용자 입력 : {}", e.getRule(), e.getMessage(), e.getCauseInput(), e);
                voucherView.showExceptionMessage(format("{0} | 현재 입력 : {1}", e.getMessage(), e.getCauseInput()));
            }
        }
    }

    private void executeMenu(VoucherMenu selectedMenu) {
        switch (selectedMenu) {
            case CREATE -> createVoucher();
            case READ_ALL -> readAllVouchers();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
            case HOME -> isRunning = false;
        }
    }

    private void createVoucher() {
        String typeOption = voucherView.readVoucherTypeOption();
        VoucherType voucherType = VoucherType.from(typeOption);

        String discountAmount = voucherView.readDiscountAmount(voucherType);

        VoucherCreateRequestDto request = new VoucherCreateRequestDto(voucherType, discountAmount);
        VoucherResponseDto response = voucherController.create(request);

        voucherView.showVoucherCreationSuccessMessage(response);
    }

    private void readAllVouchers() {
        List<VoucherResponseDto> response = voucherController.findAllVouchers();
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
