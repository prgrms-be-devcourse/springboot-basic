package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.dto.CustomerResponseDto;
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
    private static final String DELETE_VOUCHER_ID_INPUT_MESSAGE = "\n삭제할 할인권의 ID를 입력하세요.";
    private static final String VOUCHER_DELETE_SUCCESS_MESSAGE = "\n할인권이 정상적으로 삭제되었습니다.";

    private static final String CUSTOMER_NICKNAME_INPUT_MESSAGE = "\n새 고객의 닉네임을 입력하세요.(소문자 알파벳과 숫자만 가능)";
    private static final String CUSTOMER_CREATION_SUCCESS_MESSAGE = "\n고객 생성이 완료되었습니다.";

    private final InputView inputView;
    private final OutputView outputView;

    public String readMenuOption() {
        outputView.printWithLineBreak(MENU_TITLE);
        for (Menu menu : Menu.values()) {
            outputView.printWithLineBreak(menu);
        }

        return readUserInput();
    }

    public String readVoucherTypeNumber() {
        outputView.printWithLineBreak(VOUCHER_TYPE_SELECTION_MESSAGE);
        for (VoucherType voucherType : VoucherType.values()) {
            outputView.printWithLineBreak(voucherType);
        }

        return readUserInput();
    }

    public String readDiscountAmount(String message) {
        outputView.printWithLineBreak(message);

        return readUserInput();
    }

    public String readVoucherIdToUpdate() {
        outputView.printWithLineBreak(UPDATE_VOUCHER_ID_INPUT_MESSAGE);

        return readUserInput();
    }

    public String readVoucherDiscountAmountToUpdate(VoucherResponseDto findResponse) {
        outputView.printWithLineBreak(UPDATE_VOUCHER_INFORMATION_MESSAGE);
        outputView.printWithLineBreak(findResponse);
        VoucherType voucherType = findResponse.getType();

        return readDiscountAmount(voucherType.getMessage());
    }

    public String readVoucherIdToDelete() {
        outputView.printWithLineBreak(DELETE_VOUCHER_ID_INPUT_MESSAGE);

        return readUserInput();
    }

    public String readCustomerNickname() {
        outputView.printWithLineBreak(CUSTOMER_NICKNAME_INPUT_MESSAGE);

        return readUserInput();
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

    public void showVoucherDeleteSuccessMessage() {
        outputView.printWithLineBreak(VOUCHER_DELETE_SUCCESS_MESSAGE);
    }

    public void showCustomerCreationSuccessMessage(CustomerResponseDto response) {
        outputView.printWithLineBreak(CUSTOMER_CREATION_SUCCESS_MESSAGE);
        outputView.printWithLineBreak(response);
    }

    private String readUserInput() {
        outputView.printWithoutLineBreak(INPUT_MESSAGE);

        return inputView.inputWithTrimming();
    }
}
