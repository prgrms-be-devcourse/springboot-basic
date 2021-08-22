package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.voucher.VoucherUtils;

import java.text.MessageFormat;
import java.util.Scanner;

public class CommandLineApplication {
    public static void main(String[] args) {
        var voucherUtils = new VoucherUtils();

        String startMessage = "=== Voucher Program ===";
        String helpMessage = """
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";
        String createInfoMessage = """
                input voucher info
                <vouhcerType> <data>
                voucherType
                \tP : FixedAmountVoucher
                \tF : PercentDiscountVoucher
                data is long value
                ex) P 10""";
        System.out.println(startMessage);
        System.out.println(helpMessage);

        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (true) {
            String commandLine = scanner.nextLine();
            String[] splitList = commandLine.split(" ");
            command = splitList[0];
            if (command.equals("create")) {
                System.out.println(createInfoMessage);
                String[] createCommand = scanner.nextLine().split(" ");
                voucherUtils.createVoucher(createCommand);
            } else if (command.equals("list")) {
                voucherUtils.printAll();
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println(MessageFormat.format("[ERROR]Invalid command type \nYour input : {0}", command));
                System.out.println(helpMessage);
                System.out.println("=================");
            }
        }
    }
}
