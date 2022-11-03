package org.prgrms.kdt.io;

public interface IO {

    void doOutput(String text);

    String getInput();

    default void outputCommands() {
        System.out.println("=== Voucher Program ===" + System.lineSeparator() +
                "Type exit to exit the program." + System.lineSeparator() +
                "Type create to create a new voucher." + System.lineSeparator() +
                "Type list to list all vouchers" + System.lineSeparator()
        );
    }

}
