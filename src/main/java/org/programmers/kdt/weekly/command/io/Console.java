package org.programmers.kdt.weekly.command.io;

import java.util.Scanner;
import org.programmers.kdt.weekly.command.CommandType;
import org.springframework.stereotype.Component;

@Component
public class Console {

    private static final Scanner sc = new Scanner(System.in);

    public String getUserInput() {
        return sc.nextLine();
    }

    public void newLinePrint() {
        System.out.println();
    }

    public void programExitMessage() {
        System.out.println("Terminates the program.");
    }

    public void printVoucherMessage() {
        System.out.println("=== Voucher Program ===");
        CommandType.findByProgramType("start").forEach((v) -> System.out.println(v.getCommandMessage()));
    }

    public void printInputErrorMessage(InputErrorType inputErrorType) {
        System.out.println(InputErrorType.getMessage(inputErrorType));
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
        CommandType.findByProgramType("wallet").forEach((v) -> System.out.println(v.getCommandMessage()));
    }

    public void printInputMessage(String type) {
        System.out.println("input " + type);
    }

    public void printCustomerCommand() {
        System.out.println("=== Customer Menu ===");
        CommandType.findByProgramType("customer").forEach((v) -> System.out.println(v.getCommandMessage()));
    }
}