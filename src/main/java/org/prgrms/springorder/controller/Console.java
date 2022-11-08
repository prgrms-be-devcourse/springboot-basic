package org.prgrms.springorder.controller;

import java.util.List;
import org.prgrms.springorder.domain.VoucherType;
import org.prgrms.springorder.request.VoucherCreateRequest;

public class Console  {

    private final Input input;

    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void showMessages(String... messages) {
        output.showMessages(messages);
    }

    public void showMessages(List<String> messages) {
        if (messages.isEmpty()) {
            output.showMessages("저장된 데이터가 없습니다. ");
            return;
        }

        output.showMessages(messages.toArray(String[]::new));
    }

    public String input() {
        return input.input();
    }

    public long inputStringToLong() {
        String inputString = input();

        try {
            return Long.parseLong(inputString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력값은 숫자여야 합니다.", e);
        }
    }

    public VoucherCreateRequest getVoucherCreateRequest() {
        output.showMessages("select voucherType 'fixed' or 'percent' : ");
        String inputVoucherType = input();
        output.showMessages("input discount amount : ");
        long discountAmount = inputStringToLong();

        VoucherType voucherType = VoucherType.of(inputVoucherType);
        return VoucherCreateRequest.of(voucherType, discountAmount);
    }

}
