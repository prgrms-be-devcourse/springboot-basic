package org.programmers.kdt.weekly.command.io;

import java.util.Arrays;
import java.util.Scanner;
import org.programmers.kdt.weekly.command.CustomerCommandType;
import org.programmers.kdt.weekly.command.StartCommandType;
import org.programmers.kdt.weekly.command.VoucherWalletCommandType;
import org.springframework.stereotype.Component;

@Component
public class Console {

    private static final Scanner sc = new Scanner(System.in);

    public String getUserInput() {
        return sc.nextLine();
    }

    public void programExitMessage() {
        System.out.println("Terminates the program.");
    }

    public void printVoucherMessage() {
        System.out.println("=== Voucher Program ===");
        Arrays.stream(StartCommandType.values()).filter((v) -> !v.equals(StartCommandType.DEFAULT))
            .forEach((v) -> System.out.println(v.getCommandMessage()));
    }

    public void printErrorMessage(ErrorType errorType) {
        System.out.println(ErrorType.getMessage(errorType));
        System.out.println();
    }

    public void printVoucherSelectMessage() {
        System.out.println("Select a voucher type");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Selected : ");
    }

    public void printVoucherDiscountSelectMessage() {
        System.out.println("Please enter the discount value.");
        System.out.print("ENTER : ");
    }

    public void printExecutionSuccessMessage() {
        System.out.println("success!!");
    }

    public void printVoucherWalletCommand() {
        System.out.println("=== Voucher Wallet Menu ===");
        Arrays.stream(VoucherWalletCommandType.values())
            .filter((v) -> !v.equals(VoucherWalletCommandType.DEFAULT))
            .forEach((v) -> System.out.println(v.getCommandMessage()));
    }

    public void printInputMessage(String type) {
        System.out.println("input " + type);
    }

    public void printCustomerCommand() {
        System.out.println("=== Customer Menu ===");
        Arrays.stream(CustomerCommandType.values())
            .filter((v) -> !v.equals(CustomerCommandType.DEFAULT))
            .forEach((v) -> System.out.println(v.getCommandMessage()));
    }
}