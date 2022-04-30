package org.prgms.kdt.application.io;

import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputConsole implements Output{

    @Override
    public void commandTypeMessage() {
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        System.out.println("Type blacklist to list all customer blacklist");
    }

    @Override
    public void voucherTypeMessage() {
        System.out.println("\nChoose the option you want!");
        System.out.println("1. create FixedAmountVoucher (fixedAmount)");
        System.out.println("2. create PercentDiscountVoucher (percentDiscount)");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        System.out.print("\n");
        if (voucherList.isEmpty()) {
            System.out.println("voucherList is Empty");
            return;
        }
        voucherList.forEach(System.out::println);
        System.out.print("\n");
    }

    @Override
    public void printExit() {
        System.out.println("\nApplication Exit\n");
    }

    @Override
    public void printBlackList(List<Customer> blacklist) {
        System.out.print("\n");
        if (blacklist.isEmpty()) {
            System.out.println("blacklist is Empty");
            return;
        }
        blacklist.forEach(System.out::println);
        System.out.print("\n");
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
