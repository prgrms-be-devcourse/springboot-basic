package org.prgrms.kdtspringdemo.view;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

@Component
public class OutputConsole {
    private final String start_string = "> ";

    public void start() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create the program.");
        System.out.println("Type list to list all vouchers.");
        System.out.print(start_string);
    }

    public void printVoucher(Voucher voucher) {
        System.out.println("=====================================");
        System.out.println("voucherId : " + voucher.getVoucherId());
        System.out.println("voucherAmount : " + voucher.getAmount());
        System.out.println("voucherType : " + voucher.getVoucherType());
        System.out.println();
    }

    public void printCustomer(Customer customer) {
        System.out.println("=====================================");
        System.out.println("customerId : " + customer.getCustomerId());
        System.out.println("name : " + customer.getName());
        System.out.println("isBlack : " + customer.isBlack());
        System.out.println();
    }

    public void getVoucherType() {
        System.out.println("==============================================");
        System.out.println("Enter Voucher Type (fixedAmount / percentDiscount)");
        System.out.print(start_string);
    }

    public void getVoucherAmount() {
        System.out.println("Enter Voucher Amount or Percent");
        System.out.print(start_string);
    }

    public void printNumberFormatException() {
        System.out.println("올바른 숫자 형식이 아닙니다.");
    }
}
