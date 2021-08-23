package org.programmers.console;

public enum InputType {
    EXIT("exit"),
    CREATE("create"),
    VOUCHERLIST("voucherlist"),
    BLACKLIST("blacklist"),
    INPUT_ERROR("");

    private String input;

    InputType(String input) {
        this.input = input;
    }

    public static InputType getInputType(String input) {
        for (InputType inputType : InputType.values()) {
            if (inputType.input.equals(input)) {
                return inputType;
            }
        }

        return INPUT_ERROR;
    }
}
