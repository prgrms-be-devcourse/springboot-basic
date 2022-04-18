package org.prgrms.kdt.domain.console;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;

public class Output {

    private Output() {

    }

    public static void printVoucherProgram() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 1 to create customer");
        System.out.println("Type 2 to list all customers");
        System.out.println("Type 3 to list customers who have specific voucher");
        System.out.println("Type 4 to list blackList customer");
        System.out.println("Type 5 to create voucher");
        System.out.println("Type 6 to list all vouchers");
        System.out.println("Type 7 to list customer's voucher");
        System.out.println("Type 8 to remove voucher");
        System.out.println("Type 9 to assign voucher to customer");
        System.out.println("Type 10 to exit");
    }

    public static void printEnterVoucherType() {
        System.out.print("Type voucherType [FIXED or PERCENT]: ");
    }

    public static void printEnterDiscountValue() {
        System.out.print("Enter discount value: ");
    }

    public static void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }

    public static void printAllVouchers(List<Voucher> vouchers) {
        if(vouchers.isEmpty()){
            System.out.println("저장된 바우처가 없습니다.");
        }
        vouchers.forEach(System.out::println);
    }

    public static void printAllCustomers(List<Customer> customers) {
        if(customers.isEmpty()) {
            System.out.println("등록된 고객이 없습니다.");
        }
        customers.forEach(System.out::println);
    }

    public static void printEnterVoucherId() {
        System.out.print("Enter Voucher Id: ");
    }

    public static void printEnterCustomerId() {
        System.out.print("Enter Customer Id: ");
    }

    public static void printEnterCustomerType() {
        System.out.print("Enter customerType [NORMAL or BLACK]: ");
    }

    public static void printEnterName() {
        System.out.print("Enter customer name: ");
    }

    public static void printEnterEmail() {
        System.out.print("Enter customer email: ");
    }

    public static void printEnterCreatedDate() {
        System.out.println("ex) 2022-04-18");
        System.out.print("Enter Date : ");
    }
}
