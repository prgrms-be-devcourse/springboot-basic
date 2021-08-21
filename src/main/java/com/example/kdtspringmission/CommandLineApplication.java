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
                String voucher = scanner.nextLine();
                if (voucher.equals("1")) {
                    voucherRepository.insert(VoucherFactory.create("FixedAmountVoucher"));
                } else if (voucher.equals("2")) {
                    voucherRepository.insert(VoucherFactory.create("RateAmountVoucher"));
                } else {
                    throw new IllegalArgumentException();
                }
            }

            if (userInput.equals("list")) {
                System.out.println(voucherRepository.findAll());
            }
        }
    }

}
