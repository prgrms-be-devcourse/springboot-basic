package org.prgrms.vouchermanagement.view;

import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;

public class ConsoleOutput {

    private static final String welcome = """
                            
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
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
