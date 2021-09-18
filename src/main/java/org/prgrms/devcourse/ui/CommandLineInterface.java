package org.prgrms.devcourse.ui;

import org.prgrms.devcourse.blackuser.BlackUser;
import org.prgrms.devcourse.customer.Customer;
import org.prgrms.devcourse.voucher.Voucher;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterface implements UserInterface {

    private Scanner scanner;

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void showVoucherProgramMenuInterface() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        System.out.println("Type blacklist to list black user");
        System.out.println("Type customerlist to customer list");
    }

    @Override
    public void showVoucherTypeSelectMessage() {
        System.out.println("Type voucher type number.");
        System.out.println("1: FixedAmountVoucher, 2: PercentDiscountVoucher");
    }

    @Override
    public void showVoucherDiscountValueInputMessage() {
        System.out.println("Type discount value");
    }

    @Override
    public void showVoucherProgramTerminateMessage() {
        System.out.println("Terminate Voucher Program..");
    }

    @Override
    public void showInvalidInputMessage() {
        System.out.println("\nInvalid Input.");
        System.out.println("Type valid message.\n");
    }

    @Override
    public void showVoucherList(List<Voucher> voucherList) {
        System.out.println("-------------------------------");
        voucherList.forEach(System.out::print);
        System.out.println("-------------------------------");
    }

    @Override
    public void showBlackList(List<BlackUser> blackList) {
        System.out.println("-------------------------------");
        blackList.forEach(System.out::println);
        System.out.println("-------------------------------");
    }

    @Override
    public void showCustomerList(List<Customer> customerList) {
        System.out.println("-------------------------------");
        customerList.forEach(System.out::println);
        System.out.println("-------------------------------");
    }
}
