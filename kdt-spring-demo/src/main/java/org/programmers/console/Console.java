package org.programmers.console;

import org.programmers.customer.model.Customer;
import org.programmers.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String initInput() {
        return scanner.nextLine();
    }

    @Override
    public String getVoucherType() {
        return scanner.nextLine();
    }

    @Override
    public long getVoucherNumber() {
        String input = scanner.nextLine();
        return Long.parseLong(input);
    }

    @Override
    public void initInfoOutput() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type voucherlist to list all vouchers.");
        System.out.println("Type blacklist to list all blacklist.");
    }

    @Override
    public void createTypeInfoOutput() {
        System.out.println("Which types of voucher do you want to create?");
        System.out.println("Please type the number of option.");
        System.out.println("Fixed / Percent");
    }

    @Override
    public void showInputError() {
        System.out.println("You typed wrong input.");
        System.out.println("You should type exit or create or list.");
        System.out.println();
    }

    @Override
    public void listInfoOutput() {
        System.out.println("=== Voucher List ===");
    }

    @Override
    public void exitOutput() {
        System.out.println("THE END");
    }

    @Override
    public void createNumberInfoOutput() {
        System.out.println("Please type the number to be percent or amount.");
    }

    @Override
    public void blackListInfoOutput() {
        System.out.println("=== BlackList === ");
    }

    @Override
    public void printBlackList(List<Customer> customerList) {
        for (Customer customer : customerList) {
            System.out.println(
                    "Customer ID : " + customer.getCustomerId() + " / " + "Name : " + customer.getName()
            );
        }
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println(
                    "Voucher ID : " + voucher.getVoucherId() + " / " + "VoucherType : " + voucher.getVoucherType() + " / " + "Amount or Percent : " + voucher.getVoucherValue()
            );
        }
    }

}
