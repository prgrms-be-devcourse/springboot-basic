package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.voucher.VoucherUtils;

import java.util.Scanner;

public class CommandLineApplication {
    public static void main(String[] args) {
        var voucherUtils = new VoucherUtils();

        String startMessage = "=== Voucher Program ===";
        String helpMessage = """
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";
        System.out.println(startMessage);
        System.out.println(helpMessage);

        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("exit")) {
            String commandLine = scanner.nextLine();
            String[] splitList = commandLine.split(" ");
            command = splitList[0];
            if (command.equals("create")) {
                voucherUtils.createVoucher(splitList);
            } else if (command.equals("list")) {
                voucherUtils.printAll();
            }
        }
    }
}
