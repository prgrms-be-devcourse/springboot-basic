package com.example.kdtspringmission;

import java.util.Scanner;

public class CommandLineApplication {

    private Scanner scanner = new Scanner(System.in);
    private final VoucherRepository voucherRepository;

    public CommandLineApplication(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void run() {
        while (true) {

            System.out.println("=== Voucher Program ===\n"
                + "Type 'exit' to exit the program\n"
                + "Type 'create' to create voucher\n"
                + "Type 'list' to list vouchers");

            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) {
                break;
            }

            if (userInput.equals("create")) {
                System.out.println("Which one? (1.FixedAmountVoucher, 2.RateAmountVoucher)");
                voucherRepository.insert(VoucherFactory.create(scanner.nextLine()));
            }

            if (userInput.equals("list")) {
                System.out.println(voucherRepository.findAll());
            }
        }
    }

}
