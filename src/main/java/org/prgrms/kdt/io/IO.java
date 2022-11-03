package org.prgrms.kdt.io;

public abstract class IO {

    public static final String COMMAND_LIST_PROMPT = "=== Voucher Program ===" + System.lineSeparator() +
            "Type exit to exit the program." + System.lineSeparator() +
            "Type create to create a new voucher." + System.lineSeparator() +
            "Type list to list all vouchers" + System.lineSeparator();
    public static final String COMMAND_ERROR_PROMPT = "create, list, exit 중에 입력해주세요." + System.lineSeparator();

    public abstract void doOutput(String text);

    public abstract String getInput();

    public void outputCommands() {
        System.out.println(COMMAND_LIST_PROMPT);
    }

    public void outputCommandError() {
        System.out.println(COMMAND_ERROR_PROMPT);
    }

}
