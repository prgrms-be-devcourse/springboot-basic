package com.prgrms.springbootbasic.handler.vocuher;

import java.util.Arrays;
import java.util.function.IntPredicate;

public enum VoucherType {
    FIXED_AMOUNT("f",
            "Chose fixed amount. Type fixed amount(1 ~ 10000). Amount must be an integer.",
            "Fixed amount must be between 1 and 10000.",
            (amount) -> amount < 1 || amount > 10000),
    PERCENT("p",
            "Chose percent. Type percent amount(1 ~ 99(%)). Amount must be an integer.",
            "Percent amount must be between 1 and 99.",
            (amount) -> amount < 1 || amount > 99);

    private static final String NUMBER_FORMAT_EXCEPTION = "You must input integer."; // 얘도 MessageType으로 옮겨질 예정
    private final String inputValue;
    private final String typingMessage;
    private final String amountOutOfBoundMessage;
    private final IntPredicate amountOutOfBoundPredicate;

    VoucherType(String inputValue, String typingMessage, String amountOutOfBoundMessage, IntPredicate amountOutOfBoundPredicate) {
        this.inputValue = inputValue;
        this.typingMessage = typingMessage;
        this.amountOutOfBoundMessage = amountOutOfBoundMessage;
        this.amountOutOfBoundPredicate = amountOutOfBoundPredicate;
    }

    public String getInputValue() {
        return inputValue;
    }

    public String getTypingMessage() {
        return typingMessage;
    }

    public static VoucherType findByInputValue(String inputValue) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getInputValue().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid voucher type. Please select again."));
    }

    public int validateAmount(String amountInput) {
        try{
            int amount = Integer.parseInt(amountInput);
            if (amountOutOfBoundPredicate.test(amount)) throw new IllegalArgumentException(amountOutOfBoundMessage);
            return amount;
        } catch(NumberFormatException e){
            throw new IllegalArgumentException(NUMBER_FORMAT_EXCEPTION);
        }
    }
}
