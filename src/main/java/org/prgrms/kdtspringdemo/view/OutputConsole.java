package org.prgrms.kdtspringdemo.view;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.springframework.stereotype.Component;

@Component
public class OutputConsole {
    private final String start_string = "> ";

    public void startProgram() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type voucher to start voucher mode.");
        System.out.println("Type customer to start customer mode.");
        System.out.println("Type wallet to start wallet mode.");
        System.out.print(start_string);
    }

    public void startCustomerMode() {
        System.out.println();
        System.out.println("=== Customer Mode ===");
        System.out.println("To return to mode selection, please enter exit.");
        System.out.println("Type create to create the customer.");
        System.out.println("Type list to list all customers.");
        System.out.print(start_string);
    }

    public void startWalletMode() {
        System.out.println();
        System.out.println("=== Wallet Mode ===");
        System.out.println("To return to mode selection, please enter exit.");
        System.out.println("Type findByCustomerId : findVouchers by customerId");
        System.out.println("Type deleteVoucher : deleteVoucher by customerId & voucherId");
        System.out.println("Type insertVoucher : insertVoucher by customerId & voucherId");
        System.out.println("Type list : Wallet All List");
        System.out.print(start_string);
    }

    public void startVoucherMode() {
        System.out.println();
        System.out.println("=== Voucher Mode ===");
        System.out.println("To return to mode selection, please enter exit.");
        System.out.println("Type create to create the voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.print(start_string);
    }

    public void printVoucher(Voucher voucher) {
        System.out.println(voucher.toString());
    }

    public void printCustomer(Customer customer) {
        System.out.println(customer.toString());
    }

    public void printWallet(Wallet wallet) {
        System.out.println(wallet.toString());
    }

    public void getVoucherType() {
        System.out.println("==============================================");
        System.out.println("Enter Voucher Policy (fixedDiscount / percentDiscount)");
        System.out.print(start_string);
    }

    public void getCustomerName() {
        System.out.println("==============================================");
        System.out.println("Enter Customer Name");
        System.out.print(start_string);
    }

    public void getCustomerId() {
        System.out.println("==============================================");
        System.out.println("Enter Customer Id");
        System.out.print(start_string);
    }

    public void getVoucherId() {
        System.out.println("==============================================");
        System.out.println("Enter Voucher Id");
        System.out.print(start_string);
    }

    public void getWalletId() {
        System.out.println("==============================================");
        System.out.println("Enter Wallet Id");
        System.out.print(start_string);
    }

    public void getCustomerIsBlack() {
        System.out.println("==============================================");
        System.out.println("Enter Customer isBlack? (true / false)");
        System.out.print(start_string);
    }

    public void getVoucherAmount() {
        System.out.println("Enter Voucher Amount or Percent");
        System.out.print(start_string);
    }

    public void printProgramEnd(){
        System.out.println("<<<The program is closing>>>");
    }

    public void printVoucherModeEnd() {
        System.out.println("<< End Voucher Mode >>");
    }

    public void printCustomerModeEnd() {
        System.out.println("<< End Customer Mode >>");
    }

    public void printWalletModeEnd() {
        System.out.println("<< End Wallet Mode >>");
    }
}
