package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.VoucherResponseDto;
import com.devcourse.voucherapp.entity.dto.VouchersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewManager {

    private static final String MENU_TITLE = "\n[할인권 프로그램 v1.0]";
    private static final String INPUT_MESSAGE = "입력 : ";
    private static final String QUIT_MESSAGE = "\n프로그램을 종료합니다.";
    private static final String VOUCHER_TYPE_SELECTION_MESSAGE = "\n할인 방식을 선택하세요.";
    private static final String VOUCHER_CREATION_SUCCESS_MESSAGE = "\n할인권 생성이 완료되었습니다.";
    private static final String ALL_VOUCHERS_LIST_MESSAGE = "\n현재까지 생성된 할인권 목록입니다.";
    private static final String UPDATE_VOUCHER_ID_INPUT_MESSAGE = "\n수정할 할인권의 ID를 입력하세요.";
    private static final String UPDATE_VOUCHER_INFORMATION_MESSAGE = "\n선택하신 할인권의 정보를 수정합니다.";
    private static final String VOUCHER_UPDATE_SUCCESS_MESSAGE = "\n할인권 수정이 완료되었습니다.";

    private final InputView inputView;
    private final OutputView outputView;

    public String readMenuNumber() {
        outputView.printWithLineBreak(MENU_TITLE);
        for (Menu menu : Menu.values()) {
            outputView.printWithLineBreak(menu);
        }
        outputView.printWithoutLineBreak(INPUT_MESSAGE);

        return inputView.inputWithTrimming();
    }

    public String readVoucherTypeNumber() {
        outputView.printWithLineBreak(VOUCHER_TYPE_SELECTION_MESSAGE);
        for (VoucherType voucherType : VoucherType.values()) {
            outputView.printWithLineBreak(voucherType);
        }
        outputView.printWithoutLineBreak(INPUT_MESSAGE);

        return inputView.inputWithTrimming();
    }

    public String readDiscountAmount(String message) {
        outputView.printWithLineBreak(message);
        outputView.printWithoutLineBreak(INPUT_MESSAGE);

        return inputView.inputWithTrimming();
    }

    public String readVoucherIdToUpdate() {
        outputView.printWithLineBreak(UPDATE_VOUCHER_ID_INPUT_MESSAGE);
        outputView.printWithoutLineBreak(INPUT_MESSAGE);

        return inputView.inputWithTrimming();
    }

    public String readVoucherDiscountAmountToUpdate(VoucherResponseDto findResponse) {
        outputView.printWithLineBreak(UPDATE_VOUCHER_INFORMATION_MESSAGE);
        outputView.printWithLineBreak(findResponse);
        VoucherType voucherType = findResponse.getType();

        return readDiscountAmount(voucherType.getMessage());
    }

    public void showExceptionMessage(String message) {
        outputView.printWithLineBreak(message);
    }

    public void showQuitMessage() {
        outputView.printWithLineBreak(QUIT_MESSAGE);
    }

    public void showVoucherCreationSuccessMessage(VoucherResponseDto response) {
        outputView.printWithLineBreak(VOUCHER_CREATION_SUCCESS_MESSAGE);
        outputView.printWithLineBreak(response);
    }

    public void showAllVouchers(VouchersResponseDto response) {
        outputView.printWithLineBreak(ALL_VOUCHERS_LIST_MESSAGE);
        for (VoucherResponseDto voucher : response.getVouchers()) {
            outputView.printWithLineBreak(voucher);
        }
    }

    public void showVoucherUpdateSuccessMessage(VoucherResponseDto response) {
        outputView.printWithLineBreak(VOUCHER_UPDATE_SUCCESS_MESSAGE);
        outputView.printWithLineBreak(response);
    }
}
