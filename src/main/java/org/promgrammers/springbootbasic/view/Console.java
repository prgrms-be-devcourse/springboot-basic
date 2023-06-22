package org.promgrammers.springbootbasic.view;

import org.promgrammers.springbootbasic.domain.VoucherType;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;

public class Console {

    private final Input input;
    private final Output output;

    private static final String NUMBER_REGEX = "\\d+";

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void print(String[] messages) {
        output.print(messages);
    }

    public void print(String message) {
        output.print(message);
    }

    public String input() {
        return input.read();
    }

    public CreateVoucherRequest requestVoucherCreation() {
        String inputVoucherType = askForVoucherType();
        VoucherType voucherType = VoucherType.of(inputVoucherType);

        long discountAmount = askForDiscountAmount();
        return CreateVoucherRequest.of(voucherType, discountAmount);
    }

    private String askForVoucherType() {
        output.print("Voucher 타입을 'fixed', 'percent'중 골라주세요. : ");
        return input();
    }

    private long askForDiscountAmount() {
        output.print("할인 정책에 맞는 할인 금액을 입력해 주세요. : ");
        String inputString = input();
        if (!inputString.matches(NUMBER_REGEX)) {
            throw new IllegalArgumentException("입력값은 숫자여야 합니다.");
        }
        return Long.parseLong(inputString);
    }
}
