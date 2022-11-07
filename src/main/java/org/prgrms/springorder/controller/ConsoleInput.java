package org.prgrms.springorder.controller;

import java.io.BufferedReader;
import java.io.IOException;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;
import org.springframework.util.StringUtils;

public class ConsoleInput {

    private final BufferedReader bufferedReader;

    public ConsoleInput(BufferedReader reader) {
        this.bufferedReader = reader;
    }

    public String inputString() {
        try {
            String input = bufferedReader.readLine();
            validateEmptyInput(input);
            return input;
        } catch (IOException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.", e);
        }
    }

    public long inputStringToLong() {
        String inputString = inputString();

        try {
            return Long.parseLong(inputString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력값은 숫자여야 합니다.", e);
        }

    }

    private void validateEmptyInput(String input) {
        if (!StringUtils.hasText(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    public VoucherCreateRequest getVoucherCreateRequest(ConsoleOutput consoleOutput) {
        consoleOutput.showMessage("select voucherType 'fixed' or 'percent' : ");
        String inputVoucherType = inputString();
        consoleOutput.showMessage("input discount amount : ");
        long discountAmount = inputStringToLong();

        VoucherType voucherType = VoucherType.of(inputVoucherType);
        return VoucherCreateRequest.of(voucherType, discountAmount);
    }

}