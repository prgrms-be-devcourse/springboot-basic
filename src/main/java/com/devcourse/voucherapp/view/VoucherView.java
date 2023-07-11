package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.response.VoucherResponseDto;
import com.devcourse.voucherapp.entity.voucher.response.VouchersResponseDto;
import com.devcourse.voucherapp.view.io.Output;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherView {

    private static final String VOUCHER_TITLE = "\n[할인권 메뉴]";
    private static final String VOUCHER_TYPE_SELECTION_MESSAGE = "\n할인 방식을 선택하세요.";
    private static final String VOUCHER_CREATION_SUCCESS_MESSAGE = "\n할인권 생성이 완료되었습니다.";
    private static final String ALL_VOUCHERS_LIST_MESSAGE = "\n현재까지 생성된 할인권 목록입니다.";
    private static final String UPDATE_VOUCHER_ID_INPUT_MESSAGE = "\n수정할 할인권의 ID를 입력하세요.";
    private static final String UPDATE_VOUCHER_INFORMATION_MESSAGE = "\n선택하신 할인권의 정보를 수정합니다.";
    private static final String VOUCHER_UPDATE_SUCCESS_MESSAGE = "\n할인권 수정이 완료되었습니다.";
    private static final String DELETE_VOUCHER_ID_INPUT_MESSAGE = "\n삭제할 할인권의 ID를 입력하세요.";
    private static final String VOUCHER_DELETE_SUCCESS_MESSAGE = "\n할인권이 정상적으로 삭제되었습니다.";

    private final Output output;
    private final CommonView commonView;

    public String readVoucherTypeNumber() {
        output.printWithLineBreak(VOUCHER_TYPE_SELECTION_MESSAGE);
        commonView.showElementsInArray(VoucherType.values());

        return commonView.readUserInput();
    }

    public String readDiscountAmount(String message) {
        output.printWithLineBreak(message);

        return commonView.readUserInput();
    }

    public String readVoucherIdToUpdate() {
        output.printWithLineBreak(UPDATE_VOUCHER_ID_INPUT_MESSAGE);

        return commonView.readUserInput();
    }

    public String readVoucherDiscountAmountToUpdate(VoucherResponseDto findResponse) {
        output.printWithLineBreak(UPDATE_VOUCHER_INFORMATION_MESSAGE);
        output.printWithLineBreak(findResponse);

        VoucherType voucherType = findResponse.getType();

        return readDiscountAmount(voucherType.getMessage());
    }

    public String readVoucherIdToDelete() {
        output.printWithLineBreak(DELETE_VOUCHER_ID_INPUT_MESSAGE);

        return commonView.readUserInput();
    }

    public void showTitle() {
        output.printWithLineBreak(VOUCHER_TITLE);
    }

    public void showVoucherCreationSuccessMessage(VoucherResponseDto response) {
        output.printWithLineBreak(VOUCHER_CREATION_SUCCESS_MESSAGE);
        output.printWithLineBreak(response);
    }

    public void showAllVouchers(VouchersResponseDto response) {
        output.printWithLineBreak(ALL_VOUCHERS_LIST_MESSAGE);
        commonView.showElementsInList(response.getVouchers());
    }

    public void showVoucherUpdateSuccessMessage(VoucherResponseDto response) {
        output.printWithLineBreak(VOUCHER_UPDATE_SUCCESS_MESSAGE);
        output.printWithLineBreak(response);
    }

    public void showVoucherDeleteSuccessMessage() {
        output.printWithLineBreak(VOUCHER_DELETE_SUCCESS_MESSAGE);
    }
}
