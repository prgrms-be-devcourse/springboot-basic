package org.prgrms.vouchermanagement.view;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;

public class ConsoleOutput {

    private static final String welcome = """
                            
            === Voucher Program ===
            Type create to create a new voucher.
            Type list to list all vouchers.
            Type blacklist to list customer in blacklist
            Type exit to exit the program.
            """;

    private static final String createVoucher = """
                            
            === CREATE VOUCHER ===
            select policy(fixed, percent)
            input amount or percent
            """;

    private static final String createVoucherComplete = """
                            
            === COMPLETE CREATE VOUCHER ===
            """;

    private static final String showVoucherLists = """
                            
            === VOUCHER LISTS ===
            """;

    private static final String showBlackList = """
                            
            === CUSTOMER BLACKLIST ===
            """;

    private static final String exit = """
            
            === Bye Bye ===
            """;

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printlnMessage(String message) {
        System.out.println(message);
    }

    public void getInput() {
        System.out.print("> ");
    }

    public void printWelcomeMessage() {
        printMessage(welcome);
    }

    public void printCreateVoucherMessage() {
        printMessage(createVoucher);
    }

    public void printCreateVoucherCompleteMessage() {
        printMessage(createVoucherComplete);
    }

    public void printBlackList(List<Customer> blackList) {
        printMessage(showBlackList);
        for(Customer customer : blackList) {
            printlnMessage("customerId : " + customer.getCustomerId() + ", name : " + customer.getName() + ", age : " + customer.getAge());
        }
    }

    public void printVouchers(List<Voucher> vouchers) {
        printMessage(showVoucherLists);
        for(Voucher voucher : vouchers) {
            printlnMessage("voucherId : " + voucher.getVoucherId() + ", policy : " + voucher.getDiscountPolicy().getPolicyStatus());
        }
    }

    public void printExitMessage() {
        printMessage(exit);
    }

}
