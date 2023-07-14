package com.devcourse.voucherapp.view;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.entity.voucher.dto.VoucherResponseDto;
import com.devcourse.voucherapp.view.io.Output;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoucherView {

    private static final String VOUCHER_TITLE = "\n[할인권 메뉴]";
    private static final String VOUCHER_TYPE_SELECTION_MESSAGE = "\n할인 방식을 선택하세요.";
    private static final String DISCOUNT_AMOUNT_INPUT_FORMAT = "\n{0} 수치를 입력하세요. ({1}, 단위: {2})";
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

    public String readDiscountAmount(VoucherType voucherType) {
        String name = voucherType.getName();
        String condition = voucherType.getCondition();
        String unit = voucherType.getUnit();

        String discountInputMessage = format(DISCOUNT_AMOUNT_INPUT_FORMAT, name, condition, unit);
        output.printWithLineBreak(discountInputMessage);

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

        return readDiscountAmount(voucherType);
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

    public void showAllVouchers(List<VoucherResponseDto> response) {
        output.printWithLineBreak(ALL_VOUCHERS_LIST_MESSAGE);
        commonView.showElementsInList(response);
    }

    public void showVoucherUpdateSuccessMessage(VoucherResponseDto response) {
        output.printWithLineBreak(VOUCHER_UPDATE_SUCCESS_MESSAGE);
        output.printWithLineBreak(response);
    }

    public void showVoucherDeleteSuccessMessage() {
        output.printWithLineBreak(VOUCHER_DELETE_SUCCESS_MESSAGE);
    }
}
