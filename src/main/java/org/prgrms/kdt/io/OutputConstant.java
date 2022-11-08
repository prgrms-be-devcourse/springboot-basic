package org.prgrms.kdt.io;

public enum OutputConstant {
    CONSOLESTART("=== Voucher Program ===\n" +
            "Type 'exit' to exit the program.\n" +
            "Type 'create' to create a new voucher.\n" +
            "Type 'list' to list all vouchers."),
    SELECTWRONG("Wrong Selection. Please write again."),
    CONSOLEEND("Exit Program. Good Bye.");

    private final String outputConstant;

    OutputConstant(String printText) {
        this.outputConstant = printText;
    }

    public String getOutputConstant() {
        return outputConstant;
    }
}
