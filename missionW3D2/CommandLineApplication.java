package com.prgms.missionW3D2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CommandLineApplication {

    static void tutorial() {
        System.out.println("\n=== Voucher Program ===\n"
                + "Type exit to exit the program.\n"
                + "Type create to create a new voucher\n"
                + "Type list to list all vouchers.");
    }

    static void inputFront() {
        System.out.printf(">> ");
    }

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            tutorial(); inputFront();
            String command = sc.nextLine();
            switch (command) {
                case "exit" :
                    System.out.println("Exit the Program.");
                    System.exit(0);
                case "create" :
                    while (true) {
                        System.out.println(
                                "Type 1 to create Fixed Amount Voucher.\n" +
                                "Type 2 to create Percent Discount Voucher.\n" +
                                "Type exit to re-command."
                                );
                        inputFront();
                        String voucherType = sc.nextLine();
                        if (voucherType.equals("1")) {
                            // Fixed Amount Voucher
                            System.out.println("Type the amount of the voucher that you want to create.");
                            System.out.println("ONLY the long type is allowed.");
                            inputFront();
                            String amountStr = sc.nextLine();
                            try {
                                Long amount = Long.parseLong(amountStr);
                                Voucher newVoucher = voucherService.createVoucher(VoucherType.FixedAmountVoucher, amount);
                                System.out.println("Created "+ newVoucher.getVoucherInfo());
                            } catch (NumberFormatException e) {
                                System.out.println("What you typed is not the long type amount.");
                            }
                        } else if (voucherType.equals("2")) {
                            // Percent Discount Voucher
                            System.out.println("Type the percentage of the voucher that you want to create.");
                            System.out.println("ONLY the long type is allowed.");
                            inputFront();
                            String percentStr = sc.nextLine();
                            try {
                                Long percent = Long.parseLong(percentStr);
                                Voucher newVoucher = voucherService.createVoucher(VoucherType.PercentDiscountVoucher, percent);
                                System.out.println("Created "+ newVoucher.getVoucherInfo());
                            } catch (NumberFormatException e) {
                                System.out.println("What you typed is not the long type.");
                            }
                        } else if (voucherType.equals("exit")) {
                            // 프로그램 종료
                            break;
                        } else {
                            // 허용되지 않는 명령어를 입력받았을 때
                            System.out.println("You typed wrong. Try again.");
                        }
                    }
                    break;
                case "list" :
                    List<Voucher> list = voucherService.getVoucherList();
                    if (list.size() == 0) {
                        System.out.println("There's no voucher. Create a voucher.");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).getVoucherInfo());
                        }
                    }
                    break;
                default:
                    System.out.println("You typed the wrong command. Try again.");
            }
        }

    }
}
