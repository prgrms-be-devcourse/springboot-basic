package com.dojinyou.devcourse.voucherapplication.view;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleView implements InputView<String>, OutputView<String> {
    private static final String EMPTY_INPUT_ERROR_MESSAGE = "ERROR: 입력이 없습니다";
    private static final String EMPTY_OUTPUT_ERROR_MESSAGE = "ERROR: 출력이 없습니다";
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String getUserInput() {
        String userInput = scanner.nextLine().strip();
        validateEmptyInput(userInput);
        return userInput;
    }

    @Override
    public VoucherType getVoucherType() {
        String userInputVoucherType = getUserInput().toUpperCase();
        return VoucherType.from(userInputVoucherType);
    }

    @Override
    public VoucherAmount getVoucherAmount(VoucherType voucherType) {
        int userInputAmount = Integer.parseInt(getUserInput());
        return VoucherAmount.of(voucherType, userInputAmount);
    }


    private void validateEmptyInput(String userInput) {
        if (userInput.length() == 0) {
            throw new IllegalArgumentException(EMPTY_INPUT_ERROR_MESSAGE);
        }
    }

    @Override
    public void display(String output) {
        if (output == null) {
            throw new IllegalArgumentException(EMPTY_OUTPUT_ERROR_MESSAGE);
        }
        output = output.trim();
        validateEmptyOutput(output);
        System.out.print(output + "\n");
    }

    private void validateEmptyOutput(String output) {
        if (output.length() == 0) {
            throw new IllegalArgumentException(EMPTY_OUTPUT_ERROR_MESSAGE);
        }
    }
}
