package org.promgrammers.springbootbasic.view;

public class Console {

    private final Input input;
    private final Output output;

    private static final String NUMBER_REGEX = "-?\\d+";
    private static final String[] COMMAND_GUIDE_MESSAGES = {
            "=== Voucher Program ===",
            "Type '1' to exit the program.",
            "Type '2' to create a new voucher.",
            "Type '3' to list all vouchers.",
            "Type '4' to show blackList Customer"
    };
    private static final String[] VOUCHER_CHOICE_GUIDE_MESSAGES = {
            "=== Voucher 타입을 골라주세요. ===",
            "Type '1' is 'Fixed' Voucher.",
            "Type '2' is 'Percent' Voucher."
    };

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void print(String message) {
        output.print(message);
    }

    public String input() {
        return input.read();
    }

    public String askForVoucherType() {
        output.print(VOUCHER_CHOICE_GUIDE_MESSAGES);
        return input();
    }

    public long askForDiscountAmount() {
        output.print("할인 정책에 맞는 할인 금액을 입력해 주세요. : ");
        String inputString = input();
        if (!inputString.matches(NUMBER_REGEX)) {
            throw new IllegalArgumentException("입력값은 숫자여야 합니다. => " + inputString);
        }
        return Long.parseLong(inputString);
    }

    public void displayCommandGuide() {
        output.print(COMMAND_GUIDE_MESSAGES);
    }
}
