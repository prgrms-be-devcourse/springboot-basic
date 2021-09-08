package org.prgrms.kdt.assignments;

import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineApplication implements Runnable {

    public static Scanner scanner = new Scanner(System.in);

    private final CreateVoucher createVoucher;
    private final VoucherService voucherService;

    public CommandLineApplication(CreateVoucher createVoucher,VoucherService voucherService) {
        this.createVoucher = createVoucher;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        Console console = new Console();
        console.guide();

        while(true) {
            switch (scanner.nextLine().toUpperCase()){
                case "CREATE" -> {
                    VoucherData voucherData = console.inputVoucher();
                    System.out.println("어플리케이션에서 확인한 voucherNumber " + voucherData.getVoucherNumber());
                    System.out.println("어플리케이션에서 확인한 discountAmount " + voucherData.getDiscountAmount());
                    createVoucher.create(voucherData);
                    console.successfullyCreated();
                }
                case "LIST" -> {
                    console.printVoucherList(voucherService.getAllVoucher());
                }
                case "EXIT" -> {
                    console.exit();
                    System.exit(0);
                }
                default -> console.commandError();
            }
        }
    }
}
