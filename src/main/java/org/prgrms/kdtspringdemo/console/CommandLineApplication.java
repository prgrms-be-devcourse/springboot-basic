package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.AppConfiguration;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    static VoucherService voucherService;
    static VoucherRepository voucherRepository;
    public static void main(String[] args) {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = application.getBean(VoucherService.class);
        voucherRepository = application.getBean(VoucherRepository.class);

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
                readAllVoucher();
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

    public static void readAllVoucher()
    {
        System.out.println("This is all list");
        List<Voucher> allVoucher = voucherRepository.findAll();
        allVoucher.forEach(System.out::println);
    }
}
