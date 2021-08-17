package org.programmers.kdt;

import org.programmers.kdt.utils.MyUtils;
import org.programmers.kdt.voucher.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        printWelcome();
        Scanner sc = new Scanner(System.in);
        boolean actionDone = false;

        String command = sc.next();
        while (!command.equals("exit")) {
            if (actionDone) {
                printWelcome();
                command = sc.next();
                actionDone = false;
            }

            switch (command) {
                case "create":
                    while (!actionDone) {
                        System.out.print("Choose your voucher type(FixedAmountVoucher = fixed, PercentDiscountVoucher = percent) : ");
                        command = sc.next();

                        switch (command.toLowerCase()) {
                            case "fixed":
                                System.out.print("How much of a discount do you want to give? ");
                                command = sc.next();
                                while (!MyUtils.isDigits(command)) {
                                    System.out.println("Invalid Input. Only digits are allowed");
                                    System.out.print("How much of a discount do you want to give? ");
                                    command = sc.next();
                                }
                                Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(command));
                                VoucherService voucherService = applicationContext.getBean(VoucherService.class);
                                voucherService.addVoucher(voucher);
                                actionDone = true;
                                printSuccessAddVoucher(voucher);
                                break;

                            case "percent":
                                System.out.print("How much percent of a discount do you want to give? ");
                                command = sc.next();
                                while (!MyUtils.isDigits(command)) {
                                    System.out.println("Invalid Input. Only digits are allowed");
                                    System.out.print("How much percent of a discount do you want to give? ");
                                    command = sc.next();
                                }

                                if (Integer.parseInt(command) < 0 || Integer.parseInt(command) > 100) {
                                    System.out.println("Invalid Input. Valid Range : [0, 100]");
                                    break;
                                }

                                voucher = new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(command));
                                voucherService = applicationContext.getBean(VoucherService.class);
                                voucherService.addVoucher(voucher);
                                printSuccessAddVoucher(voucher);
                                actionDone = true;
                                break;

                            case "exit":
                                System.out.println("Terminating...");
                                return;

                            default:
                                System.out.println("Invalid input. You can use \"fixed\" or \"percent\"");
                        }
                    }
                    break;

                case "list":
                    System.out.println();
                    System.out.println("Print all voucher info...");
                    VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
                    voucherRepository.listAllVouchers();
                    actionDone = true;
                    break;

                case "exit":
                    System.out.println("Terminating...");
                    return;

                default:
                    System.out.println("Invalid Input. Please enter right input(create, list, exit).");
                    System.out.print(">>> ");
                    command = sc.next();
            }
        }

        System.out.println("Terminating...");
    }

    private static void printWelcome() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.print(">>> ");
    }

    private static void printSuccessAddVoucher(Voucher voucher) {
        System.out.println();
        System.out.println("** Your new voucher has been added **");
        System.out.println(voucher);
    }
}
