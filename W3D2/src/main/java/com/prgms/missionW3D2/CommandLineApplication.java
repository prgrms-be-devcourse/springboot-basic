package com.prgms.missionW3D2;

import com.prgms.missionW3D2.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class CommandLineApplication {

    static void tutorial() {
        System.out.println("\n=== Voucher Program ===\n"
                + "Type exit to exit the program.\n"
                + "Type create to create a new voucher\n"
                + "Type list to list all vouchers.");
    }

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            tutorial();
            System.out.printf(">> ");
            String command = sc.nextLine(); // create or list
            CommandType.of(command).doCommand(voucherService);
        }

    }
}
