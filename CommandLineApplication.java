package org.prgrms.kdtspringorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CommandLineApplication {
    static void tutorial() {
        System.out.println("\n=== Voucher Program ===\n"
                + "Type exit to exit the program.\n"
                + "Type create to create a new voucher\n"
                + "Type list to list all vouchers.");
    }

    public static void main(String[] args) throws IOException {

        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();
        var voucherService = applicationContext.getBean(VoucherService.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            tutorial();
            System.out.printf(">> ");
            String command = sc.nextLine(); // create or list

            Command commandEnum = Command.from(command);
            commandEnum.accept(voucherService);
        }
    }
}

