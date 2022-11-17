package com.programmers.VoucherManagementApplication.dto;

public class Amount {

    private final Long amount;

    public Amount(String amount) {
        validate(amount);
        this.amount = Long.parseLong(amount);
    }

    private void validate(String amount) {
        validateAmountIsNotNumber(amount);
        validateAmountIsMinus(amount);
        validateAmountIsZero(amount);
    }

    private void validateAmountIsZero(String amount) {
        if (Long.parseLong(amount) == 0) throw new IllegalArgumentException("\nAmount should not be zero");
    }

    private void validateAmountIsMinus(String amount) {
        if (Long.parseLong(amount) < 0) throw new IllegalArgumentException("\nAmount should be positive");
    }

    private void validateAmountIsNotNumber(String amount) {
        if (!isDigit(amount)) throw new IllegalArgumentException("\nThe price is an integer.");
    }

    private boolean isDigit(String amount) {
        for (char x : amount.toCharArray()) {
            if (!Character.isDigit(x)) return false;
        }
        return true;
    }
    public Long getAmount() {
        return amount;
    }
}
