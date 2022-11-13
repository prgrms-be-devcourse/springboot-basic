package org.prgrms.springorder.controller;

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

}
