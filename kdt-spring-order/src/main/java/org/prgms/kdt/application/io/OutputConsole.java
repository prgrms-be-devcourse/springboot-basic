package org.prgms.kdt.application.io;

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
}
