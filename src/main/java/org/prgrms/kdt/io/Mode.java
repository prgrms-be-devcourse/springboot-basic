package org.prgrms.kdt.io;

public enum Mode {
    MODE_WALLET("wallet"),
    MODE_CUSTOMER("customer"),
    MODE_VOUCHER("voucher"),
    MODE_EXIT("exit"),
    MODE_MENU("=== Program ===" + System.lineSeparator() +
            "[1] Type 'customer' to enter a customer menu." + System.lineSeparator() +
            "[2] Type 'voucher' to enter a voucher menu." + System.lineSeparator() +
            "[3] Type 'wallet' to enter a wallet menu." + System.lineSeparator() +
            "[4] Type 'exit' to exit the program." + System.lineSeparator());

    private final String message;

    Mode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Mode fromString(String text) {
        for (Mode mode : Mode.values()) {
            if (mode.message.equalsIgnoreCase(text)) {
                return mode;
            }
        }
        return null; // You may handle null as per your requirement
    }
}
