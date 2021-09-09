package org.programmers.console;

import org.programmers.customer.model.BlackListCustomer;
import org.programmers.customer.model.Customer;
import org.programmers.voucher.model.VoucherBase;
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
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public long getVoucherNumber() {
        String input = scanner.nextLine();
        return Long.parseLong(input);
    }

    @Override
    public void initInfoOutput() {
        System.out.println();
        System.out.println("=== Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type customer to enter a customer mode.");
        System.out.println("Type voucher to enter a voucher mode.");
    }

    @Override
    public void showInputError() {
        System.out.println("You typed wrong input.");
        System.out.println();
    }

    @Override
    public void exitOutput() {
        System.out.println("THE END");
    }

    @Override
    public void exitModeOutput() {
        System.out.println("End of the Mode");
    }

    @Override
    public void customerNameInfoOutput(String mode) {
        System.out.println("Please type a name to " + mode + " the customer");
    }

    @Override
    public void customerEmailInfoOutput(String mode) {
        System.out.println("Please type a email to " + mode + " the customer");
    }

    @Override
    public void voucherTypeInfoOutput(String mode) {
        System.out.println("Please type a type to " + mode + " a voucher");
    }

    @Override
    public void voucherNumberInfoOutput(String mode) {
        System.out.println("Please type a number to " + mode + " a voucher");
    }

    @Override
    public void customerInitInfoOutput() {
        System.out.println("=== Customer Mode ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new customer.");
        System.out.println("Type find to search a customer");
        System.out.println("Type update to update a customer");
        System.out.println("Type delete to delete a voucher");
        System.out.println("Type customerlist to list all customers.");
        System.out.println("Type blacklist to list all blacklist.");
    }

    @Override
    public void voucherInitInfoOutput() {
        System.out.println("=== Voucher Mode ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type find to find a voucher");
        System.out.println("Type update to update a number of the voucher");
        System.out.println("Type delete to delete a voucher");
        System.out.println("Type voucherlist to list all vouchers.");
    }

    @Override
    public void printBlackList(List<BlackListCustomer> blackListCustomerList) {
        System.out.println("=== BlackList === ");
        for (BlackListCustomer blackListCustomer : blackListCustomerList) {
            System.out.println(blackListCustomer.toString());
        }
    }

    @Override
    public void printVoucherFileList(List<Voucher> voucherList) {
        System.out.println("=== Voucher List ===");
        for (Voucher voucher : voucherList) {
            System.out.println(voucher.toString());
        }
    }

    @Override
    public void printVoucherDataBaseList(List<VoucherBase> voucherBaseList) { //
        System.out.println("=== Voucher List ===");
        for (VoucherBase voucherBase : voucherBaseList) {
            System.out.println(voucherBase.toString());
        }
    }

    @Override
    public void printCustomerList(List<Customer> customerList) { //
        System.out.println("=== Customer List ===");
        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }
    }

    @Override
    public void printCustomer(Customer customer) { //
        System.out.println("=== Customer List ===");
        System.out.println(customer.toString());
    }

    @Override
    public void printFindVoucher(VoucherBase voucherBase) { //
        System.out.println("=== Voucher List ===");
        System.out.println(voucherBase.toString());
    }
}
