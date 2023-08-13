package org.promgrammers.voucher;

import lombok.RequiredArgsConstructor;
import org.promgrammers.voucher.controller.CustomerController;
import org.promgrammers.voucher.controller.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class VoucherApplication implements CommandLineRunner {

    private final VoucherController voucherController;
    private final CustomerController customerController;

    private void execute(String command) {
        switch (command) {
            case "VOUCHER":
                voucherController.userPostController();
                break;
            case "CUSTOMER":
                customerController.userPostController();
                break;
            default:
                System.out.println("Invalid command: " + command);
                break;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command (VOUCHER or CUSTOMER):");
        String command = scanner.nextLine().toUpperCase();
        execute(command);
    }
}

