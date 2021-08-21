package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.AppConfiguration;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.stream.Stream;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    static VoucherService voucherService;
    public static void main(String[] args) {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = application.getBean(VoucherService.class);

        String startMessage = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.""";
        System.out.println(startMessage);

        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("exit")) {
            String commandLine = scanner.nextLine();
            String[] splitList = commandLine.split(" ");
            command = splitList[0];
            if (command.equals("create")) {
                createVoucher(splitList);
            } else if (command.equals("list")) {
                voucherService.printAllVoucher();
            }
        }
    }

    public static void createVoucher(String[] splitList)
    {
        Assert.isTrue(splitList.length == 3, "This command did not receive the required arguments.");
        String voucherName = splitList[1];
        if (voucherName.equals("F")) {
            Voucher voucher = voucherService.createFixedAmountVoucher(UUID.randomUUID(), Long.parseLong(splitList[2]));
            System.out.println("This is create : " + voucher);
        } else if (voucherName.equals("P")) {
            Voucher voucher = voucherService.createPercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(splitList[2]));
            System.out.println("This is create : " + voucher);
        } else {
            System.out.println("None Voucher!!! : " + voucherName);
        }
    }
}
