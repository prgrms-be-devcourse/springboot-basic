package com.programmers.VoucherManagementApplication.dto;

import com.programmers.VoucherManagementApplication.io.Message;
import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

public class CreateVoucherRequest {

    private final VoucherType voucherType;
    private final Amount amount;

    public CreateVoucherRequest(String inputValue) {
        validate(inputValue);

        String[] request = inputValue.split(" ");
        this.voucherType = VoucherType.of(request[0]);
        this.amount = new Amount(Long.parseLong(request[1]));
    }

    private void validate(String inputValue) {
        String[] input = inputValue.split(" ");
        incorrectSizeOfInput(inputValue);
        InvalidVoucherType(input[0]);
        validateAmountIsNotNumber(input[1]);
        validateAmountIsMinus(input[1]);
        validateAmountIsZero(input[1]);
    }

    private void validateAmountIsZero(String amount) {
        if (Long.parseLong(amount) == 0) {
            throw new IllegalArgumentException(Message.INVALID_ZERO.getMessage());
        }
    }

    private void validateAmountIsMinus(String amount) {
        if (Long.parseLong(amount) < 0) {
            throw new IllegalArgumentException(Message.INVALID_MINUS.getMessage());
        }
    }

    private void validateAmountIsNotNumber(String amount) {
        if (!isDigit(amount)) {
            throw new IllegalArgumentException(Message.INVALID_INTEGER.getMessage());
        }
    }

    private boolean isDigit(String amount) {
        for (char x : amount.toCharArray()) {
            if (!Character.isDigit(x)) return false;
        }
        return true;
    }

    private void InvalidVoucherType(String voucherType) {
        VoucherType.of(voucherType);
    }

    private void incorrectSizeOfInput(String inputValue) {
        String[] input = inputValue.split(" ");
        if (input.length != 2) {
            throw new IllegalArgumentException(Message.INVALID_REQUEST_SIZE.getMessage());
        }
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Amount getAmount() {
        return amount;
    }
}
