package com.example.kdtspringmission;

import com.example.kdtspringmission.voucher.domain.VoucherFactory;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import java.util.Scanner;

public class CommandLineApplication {

    private Scanner scanner = new Scanner(System.in);
    private final VoucherRepository voucherRepository;

    public CommandLineApplication(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public static void main(String[] args) {
        AppConfig ac = new AppConfig();
        new CommandLineApplication(ac.voucherRepository()).run();
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
