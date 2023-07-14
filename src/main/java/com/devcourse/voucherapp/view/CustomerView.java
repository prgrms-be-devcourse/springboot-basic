package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.customer.CustomerMenu;
import com.devcourse.voucherapp.entity.customer.CustomerType;
import com.devcourse.voucherapp.entity.customer.dto.CustomerResponseDto;
import com.devcourse.voucherapp.view.io.Input;
import com.devcourse.voucherapp.view.io.Output;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerView {

    private static final String CUSTOMER_TITLE = "\n[고객 메뉴]";
    private static final String INPUT_MESSAGE = "입력 : ";
    private static final String CUSTOMER_NICKNAME_INPUT_MESSAGE = "\n닉네임을 입력하세요.(공백이 없는 소문자 알파벳과 숫자 조합만 가능)";
    private static final String CUSTOMER_CREATION_SUCCESS_MESSAGE = "\n고객 생성이 완료되었습니다.";
    private static final String ALL_CUSTOMERS_LIST_MESSAGE = "\n조회된 고객 목록입니다.";
    private static final String UPDATE_CUSTOMER_NICKNAME_INPUT_MESSAGE = "\n변경을 원하는 고객의 닉네임을 입력하세요.";
    private static final String UPDATE_CUSTOMER_TYPE_INPUT_MESSAGE = "\n어떤 고객 타입으로 변경하시겠습니까?";
    private static final String CUSTOMER_UPDATE_SUCCESS_MESSAGE = "\n고객 수정이 완료되었습니다.";
    private static final String DELETE_CUSTOMER_NICKNAME_INPUT_MESSAGE = "\n삭제할 고객의 닉네임을 입력하세요.";
    private static final String CUSTOMER_DELETE_SUCCESS_MESSAGE = "\n해당 고객 정보가 정상적으로 삭제되었습니다.";

    private final Input input;
    private final Output output;

    public String readUserInput() {
        output.printWithoutLineBreak(INPUT_MESSAGE);

        return input.inputWithTrimming();
    }

    public String readCustomerNickname() {
        output.printWithLineBreak(CUSTOMER_NICKNAME_INPUT_MESSAGE);

        return readUserInput();
    }

    public String readCustomerNicknameToUpdate() {
        output.printWithLineBreak(UPDATE_CUSTOMER_NICKNAME_INPUT_MESSAGE);

        return readUserInput();
    }

    public String readCustomerTypeNumber() {
        output.printWithLineBreak(UPDATE_CUSTOMER_TYPE_INPUT_MESSAGE);
        output.printElementsInArray(CustomerType.values());

        return readUserInput();
    }

    public String readCustomerNicknameToDelete() {
        output.printWithLineBreak(DELETE_CUSTOMER_NICKNAME_INPUT_MESSAGE);

        return readUserInput();
    }

    public void showMenu() {
        output.printWithLineBreak(CUSTOMER_TITLE);
        output.printElementsInArray(CustomerMenu.values());
    }

    public void showExceptionMessage(String message) {
        output.printWithLineBreak(message);
    }

    public void showCustomerCreationSuccessMessage(CustomerResponseDto response) {
        output.printWithLineBreak(CUSTOMER_CREATION_SUCCESS_MESSAGE);
        output.printWithLineBreak(response);
    }

    public void showAllCustomers(List<CustomerResponseDto> response) {
        output.printWithLineBreak(ALL_CUSTOMERS_LIST_MESSAGE);
        output.printElementsInList(response);
    }

    public void showCustomerUpdateSuccessMessage(CustomerResponseDto response) {
        output.printWithLineBreak(CUSTOMER_UPDATE_SUCCESS_MESSAGE);
        output.printWithLineBreak(response);
    }

    public void showCustomerDeleteSuccessMessage() {
        output.printWithLineBreak(CUSTOMER_DELETE_SUCCESS_MESSAGE);
    }

}
