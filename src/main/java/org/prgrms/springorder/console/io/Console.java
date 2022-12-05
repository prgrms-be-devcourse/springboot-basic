package org.prgrms.springorder.console.io;

import java.util.List;

public class Console {

    private final Input input;

    private final Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void showMessage(String message) {
        output.showMessage(message);
    }

    public void showMessages(String[] messages) {
        output.showMessages(messages);
    }

    public void showMessages(List<String> messages) {
        if (messages.isEmpty()) {
            output.showMessage("저장된 데이터가 없습니다.");
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

    public void displayCommandGuide() {
        showMessages(new String[]{"=== Voucher Program ===",
            "Type 'exit' 는 프로그램을 종료합니다. ",
            "Type 'create' 새 바우처를 생성합니다. ",
            "Type 'list' 모든 바우처 리스트를 보여줍니다.",
            "Type 'black-list' 모든 블랙리스트를 보여줍니다.",
            "Type 'post-allocate-voucher' 바우처를 고객에게 할당합니다. 고객과 바우처 모두 존재해야 합니다.",
            "Type 'get-customer-vouchers' 특정 고객의 바우처를 모두 조회합니다. 고객과 바우처가 모두 존재해야 합니다.",
            "Type 'delete-customer-vouchers' 고객이 보유한 바우처를 제거합니다. 고객과 고객이 보유한 바우처가 존재해야 합니다.",
            "Type 'get-voucher-with-customer' 특정 바우처를 보유한 고객을 조회합니다. 바우처와 바우처를 갖고있는 고객이 존재해야 합니다.",
            "Type 'create-customer 고객을 생성합니다."
        }
        );
    }
}
