package org.programmers.kdt.weekly.io;

import java.util.List;
import java.util.Scanner;
import org.programmers.kdt.weekly.customer.Customer;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.springframework.stereotype.Component;

@Component
public class Console {

    private final Scanner sc = new Scanner(System.in);

    public String getUserInput() {
        return sc.nextLine();
    }

    public void newLinePrint() {
        System.out.println();
    }

    public void programExitMessage() {
        System.out.println("Terminates the program.");
    }

    public void startMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type list -b to list all black-list");
    }

    public void inputErrorMessage(InputErrorType inputErrorType) {
        System.out.println(InputErrorType.getMessage(inputErrorType));
    }

    public void voucherSelectMessage() {
        System.out.println("Select a voucher type");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Selected : ");
    }

    public void voucherDiscountMessage() {
        System.out.println("Please enter the discount value.");
        System.out.print("ENTER : ");
    }

    public void voucherCreateSucceedMessage() {
        System.out.println("Created!!");
    }

    public void voucherListPrint(List<Voucher> voucherList) {
        voucherList.forEach((v) -> System.out.println(v.toString()));
    }

    public void customerListPrint(List<Customer> customerList) {
        customerList.forEach((c) -> System.out.println(c.toString()));
    }
}