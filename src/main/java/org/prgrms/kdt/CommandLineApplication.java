package org.prgrms.kdt;

import org.prgrms.kdt.core.Input;
import org.prgrms.kdt.core.Output;

public class CommandLineApplication {
    private static final Input input = new Console();
    private static final Output output = new Console();


    public static void main(String[] args) {
        help();
        String inputStr;
        while (true) {
            inputStr = input.input().toUpperCase();
            switch (inputStr) {
                case "EXIT" -> {
                    exit();
                    return;
                }
                case "CREATE" -> createVoucher();
                case "LIST" -> listVoucher();
                case "HELP" -> help();
                default -> output.inputError(inputStr);
            }
        }

    }

    private static void listVoucher() {
    }

    private static void createVoucher() {
    }


    private static void help() {
        var prompt = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";
        output.printMessage(prompt);
    }

    private static void exit() {
        output.printMessage("BYE!");
    }
}
