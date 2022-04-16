package org.prgrms.kdt.util;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Console{
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private static final Scanner scanner = new Scanner(System.in);

    private Console() {

    }

    public static String inputCommand() {
        System.out.println("=== Voucher Program");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        System.out.println("Type blackList to list all Black customers");
        String command = scanner.next();
        logger.info("Type command: {}", command);
        return command;
    }


    public static String inputVoucherType() {
        System.out.println("Type voucherType: FIXED or PERCENT");
        String voucherType = scanner.next();
        logger.info("Type VoucherType: {}", voucherType);
        return voucherType;
    }


    public static long inputDiscount() {
        System.out.print("Enter discount value: ");
        long discount = scanner.nextLong();
        logger.info("Enter discount value: {}", discount);
        return discount;
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
}
